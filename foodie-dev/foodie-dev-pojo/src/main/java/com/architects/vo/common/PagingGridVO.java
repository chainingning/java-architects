package com.architects.vo.common;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PagingGridVO {

    /**
     * 当前页号
     */
    private Integer page;

    /**
     * 总页数
     */
    private Integer total;

    /**
     * 总记录数
     */
    private Long records;

    /**
     * 每行显示的内容
     */
    private List<?> rows;
}