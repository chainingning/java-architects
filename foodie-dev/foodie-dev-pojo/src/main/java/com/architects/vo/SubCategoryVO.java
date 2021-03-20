package com.architects.vo;

import lombok.Data;

/**
 * @ClassName SubCategoryVo
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2021/3/20 0020
 * @Version V1.0
 **/
@Data
public class SubCategoryVO {

    private Integer subId;

    private String subName;

    private Integer subFatherId;

    private String subType;
}
