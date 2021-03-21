package com.architects.mapper;

import com.architects.my.mapper.MyMapper;
import com.architects.pojo.Items;
import com.architects.vo.item.SearchItemsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsMapper extends MyMapper<Items> {
    /**
     * 查询商品列表
     */
    List<SearchItemsVO> selectItem(@Param("paramsMap") Map<String, Object> map);

    /**
     * 通过三级分类，查询商品列表
     */
    List<SearchItemsVO> selectItemByThirdCategory(@Param("paramsMap") Map<String, Object> map);
}