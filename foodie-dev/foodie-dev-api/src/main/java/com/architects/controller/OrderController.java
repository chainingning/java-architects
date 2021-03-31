package com.architects.controller;

import com.architects.bo.SubmitOrderBO;
import com.architects.enums.OrderStatusEnum;
import com.architects.enums.PayMethodEnum;
import com.architects.enums.ResultEnum;
import com.architects.pojo.OrderStatus;
import com.architects.service.OrderService;
import com.architects.utils.JSONVO;
import com.architects.vo.order.MerchantOrdersVO;
import com.architects.vo.order.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static com.architects.controller.BaseController.PAYMENT_URL;
import static com.architects.controller.BaseController.PAY_RETURN_URL;

/**
 * @ClassName HelloController
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2020/12/31 0031
 * @Version V1.0
 **/
//@Controller
@Api(value = "订单相关",tags = {"订单相关操作"})
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private RestTemplate restTemplate;



    @ApiOperation(value = "用户下单",notes = "创建订单",httpMethod = "POST")
    @PostMapping("/create")
    public JSONVO create(SubmitOrderBO submitOrderBO){

        // 判断支付方式
        Integer payMethod = submitOrderBO.getPayMethod();
        if (Objects.equals(payMethod, PayMethodEnum.WEIXIN.getType())
                && Objects.equals(payMethod, PayMethodEnum.ALIPAY.getType())) {
            return JSONVO.errorMsg(ResultEnum.PAY_METHOD_NOT_SUPPORTED.getMessage());
        }

        // 创建订单（并没有对超卖现象做处理）
        OrderVO orderVO = orderService.createOrder(submitOrderBO);
        String orderId = orderVO.getOrderId();

        // 创建订单以后，移除购物车中已结算（已提交）的商品
        /*
          1001
          2002 -> 用户购买
          3003 -> 用户购买
          4004
         */
        // todo 整合redis之后，完善购物车中的已结算商品清除，并且同步到前端的cookie
//        CookieUtil.setCookie(httpServletRequest, httpServletResponse, FOODIE_SHOP_CART, "", true);

        // 向支付中心发送当前订单，用于保存支付中心的订单数据
        MerchantOrdersVO merchantOrdersVO = orderVO.getMerchantOrdersVO();
        merchantOrdersVO.setReturnUrl(PAY_RETURN_URL);
        // 为了方便测试购买，所有的支付金额都统一改为1分钱
        merchantOrdersVO.setAmount(1);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("imoocUserId", "2518513-1393003255");
        httpHeaders.add("password", "poiw-03or-0rop-fp2l");

        HttpEntity<MerchantOrdersVO> merchantOrdersVOHttpEntity = new HttpEntity<>(merchantOrdersVO, httpHeaders);

        ResponseEntity<JSONVO> responseEntity = restTemplate.postForEntity(PAYMENT_URL,
                merchantOrdersVOHttpEntity,
                JSONVO.class);
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            return JSONVO.errorMsg(ResultEnum.PAYMENT_CREATE_ORDER_FAIL.getMessage());
        }

        return JSONVO.ok(orderId);
    }

    /**
     * 通知订单支付（未支付）
     */
    @PostMapping("/notifyMerchantOrderPaid")
    public Integer notifyMerchantOrderPaid(String merchantOrderId){
        orderService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_PAY.getType());
        return HttpStatus.OK.value();
    }

    /**
     * 获得支付状态
     *
     * @param orderId　订单编号
     */
    @PostMapping("getPaidOrderInfo")
    public JSONVO getPaidOrderInfo(String orderId) {
        OrderStatus orderStatus = orderService.queryOrderStatusInfo(orderId);
        return JSONVO.ok(orderStatus);
    }

}
