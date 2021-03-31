package com.architects.service.impl;

import com.architects.bo.SubmitOrderBO;
import com.architects.enums.OrderStatusEnum;
import com.architects.enums.YesNoEnum;
import com.architects.idworker.Sid;
import com.architects.mapper.OrderItemsMapper;
import com.architects.mapper.OrderStatusMapper;
import com.architects.mapper.OrdersMapper;
import com.architects.pojo.*;
import com.architects.service.AddressService;
import com.architects.service.ItemService;
import com.architects.service.OrderService;
import com.architects.vo.order.MerchantOrdersVO;
import com.architects.vo.order.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @ClassName OrderServiceImpl
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2021/3/31 0031
 * @Version V1.0
 **/
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private AddressService addressService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private OrderItemsMapper orderItemsMapper;
    @Autowired
    private OrderStatusMapper orderStatusMapper;


    @Autowired
    private Sid sid;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public OrderVO createOrder(SubmitOrderBO submitOrderBO) {
        String userId = submitOrderBO.getUserId();
        String addressId = submitOrderBO.getAddressId();
        String itemSpecIds = submitOrderBO.getItemSpecIds();
        Integer payMethod = submitOrderBO.getPayMethod();
        String leftMsg = submitOrderBO.getLeftMsg();
        // 包邮费用设置为0
        int postAmount = 0;

        // 订单id
        String orderId = sid.nextShort();

        // 用户订单上的地址
        UserAddress userAddress = addressService.queryUserAddress(userId, addressId);

        // 新订单数据保存
        Orders order = Orders.builder()
                .id(orderId)
                .userId(userId)
                .receiverName(userAddress.getReceiver())
                .receiverMobile(userAddress.getMobile())
                .receiverAddress(userAddress.getProvince() + " "
                        + userAddress.getCity() + " "
                        + userAddress.getDistrict() + " "
                        + userAddress.getDetail())
                .postAmount(postAmount)
                .payMethod(payMethod)
                .leftMsg(leftMsg)
                .isComment(YesNoEnum.NO.getType())
                .isDelete(YesNoEnum.NO.getType())
                .createdTime(new Date())
                .updatedTime(new Date())
                .build();

        // 循环根据itemSpecIds保存订单商品信息表
        // 商品原价累积
        int totalAmount = 0;
        // 优惠后的实际支付价格累计
        int realPayAmount = 0;
        for (String itemSpecId : itemSpecIds.split(",")) {
            // todo 整合redis后，商品购买的数量重新从redis的购物车中获取
            int buyCount = 1;

            // 根据规格id，查询规格的具体信息，主要获取价格
            ItemsSpec itemSpec = itemService.queryItemSpecById(itemSpecId);
            totalAmount += itemSpec.getPriceNormal() * buyCount;
            realPayAmount += itemSpec.getPriceDiscount() * buyCount;

            // 根据商品Id，获取商品信息以及商品图片
            String itemId = itemSpec.getItemId();
            Items item = itemService.queryItemById(itemId);
            ItemsImg itemImg = itemService.queryItemMainImageById(itemId);

            // 循环保存子订单数据到数据库
            OrderItems orderItem = OrderItems.builder()
                    .id(sid.nextShort())
                    .orderId(order.getId())
                    .itemId(itemId)
                    .itemName(item.getItemName())
                    .itemImg(itemImg == null ? "" : itemImg.getUrl())
                    .buyCounts(buyCount)
                    .itemSpecId(itemSpecId)
                    .itemSpecName(itemSpec.getName())
                    .price(itemSpec.getPriceDiscount())
                    .build();
            orderItemsMapper.insert(orderItem);

            // 在用户提交订单以后，规格表中需要扣除库存
            itemService.decreaseItemSpecStock(itemSpecId, buyCount);
        }

        order.setTotalAmount(totalAmount);
        order.setRealPayAmount(realPayAmount);
        ordersMapper.insert(order);

        // 保存订单状态表
        OrderStatus orderStatus = OrderStatus.builder()
                .orderId(order.getId())
                .orderStatus(OrderStatusEnum.WAIT_PAY.getType())
                .createdTime(new Date())
                .build();
        orderStatusMapper.insert(orderStatus);

        // 构建商户订单，用于传给支付中心
        MerchantOrdersVO merchantOrdersVO = MerchantOrdersVO.builder()
                .merchantOrderId(orderId)
                .merchantUserId(userId)
                .amount(realPayAmount + postAmount)
                .payMethod(payMethod)
                .build();

        OrderVO build = OrderVO.builder()
                .merchantOrdersVO(merchantOrdersVO)
                .orderId(orderId).build();

        return build;
    }

}
