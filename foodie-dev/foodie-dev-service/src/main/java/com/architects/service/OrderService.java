package com.architects.service;

import com.architects.bo.SubmitOrderBO;
import com.architects.pojo.OrderStatus;
import com.architects.vo.order.OrderVO;

/**
 * @ClassName OrderService
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2021/3/31 0031
 * @Version V1.0
 **/
public interface OrderService {

    OrderVO createOrder(SubmitOrderBO submitOrderBO);

    /**
     * 修改订单状态
     *
     * @param orderId 订单Id
     * @param orderStatus 订单状态
     */
    void updateOrderStatus(String orderId, Integer orderStatus);

    /**
     * 查询订单状态
     *
     * @param orderId　订单号
     * @return 订单状态
     */
    OrderStatus queryOrderStatusInfo(String orderId);

    /**
     * 关闭超时未支付订单
     */
    void closeOrder();
}
