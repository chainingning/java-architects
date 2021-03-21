package com.architects.mapper;

import com.architects.my.mapper.MyMapper;
import com.architects.pojo.ItemsComments;
import com.architects.vo.comment.ItemCommentVO;

import java.util.List;
import java.util.Map;

public interface ItemsCommentsMapper extends MyMapper<ItemsComments> {

    int countByItemIdLevel(String itemId, int level);

    /**
     * 查询商品评价
     */
    List<ItemCommentVO> selectItemComment(Map<String, Object> map);
}