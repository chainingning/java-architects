package com.architects.service.converter;

import com.architects.vo.common.PagingGridVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @ClassName PageInfo2PagingGridResultConverter
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2021/3/21 0021
 * @Version V1.0
 **/
public class PageInfo2PagingGridResultConverter {

    public static PagingGridVO convert(List<?> list, Integer page){
        PageInfo<?> pageInfo = new PageInfo<>();
        return PagingGridVO.builder()
                .page(page)
                .total(pageInfo.getPages())
                .rows(list)
                .records(pageInfo.getTotal()).build();

    }
}
