package com.architects.vo;

import lombok.Data;

import java.util.List;

/**
 * @ClassName CategoryVO
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2021/3/20 0020
 * @Version V1.0
 **/
@Data
public class CategoryVO {

    private Integer id;

    private String name;

    private Integer fatherId;

    private String type;

    private List<SubCategoryVO> subCatList;
}
