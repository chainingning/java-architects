package com.architects.service;

import com.architects.bo.SubmitOrderBO;
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
}
