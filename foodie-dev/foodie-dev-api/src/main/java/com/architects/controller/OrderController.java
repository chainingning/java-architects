package com.architects.controller;

import com.architects.bo.SubmitOrderBO;
import com.architects.utils.JSONVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation(value = "用户下单",notes = "创建订单",httpMethod = "POST")
    @PostMapping("/create")
    public JSONVO create(SubmitOrderBO req){
        //创建订单
        //删除购物车订单
        //向支付中心发起支付请求
        return null;
    }

}
