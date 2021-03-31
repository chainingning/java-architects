package com.architects.vo.order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderVO {

    /**
     * 订单Id
     */
    private String orderId;

    /**
     * 商户订单
     */
    private MerchantOrdersVO merchantOrdersVO;
}