package com.architects.bo;

import lombok.Data;

/**
 * @ClassName SubmitOrderBO
 * @Description: 用于创建订单的对象
 * @Author ning.chai@foxmail.com
 * @Date 2021/3/25 0025
 * @Version V1.0
 **/
@Data
public class SubmitOrderBO {
    /**
     * 用户Id
     */
    private String userId;

    /**
     * 商品规格Id
     */
    private String itemSpecIds;

    /**
     * 地址Id
     */
    private String addressId;

    /**
     * 支付方式
     */
    private Integer payMethod;

    /**
     * 买家留言
     */
    private String leftMsg;
}
