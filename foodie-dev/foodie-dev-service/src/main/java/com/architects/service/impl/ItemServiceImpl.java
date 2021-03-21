package com.architects.service.impl;

import com.architects.enums.CommentLevelEnum;
import com.architects.mapper.*;
import com.architects.pojo.Items;
import com.architects.pojo.ItemsImg;
import com.architects.pojo.ItemsParam;
import com.architects.pojo.ItemsSpec;
import com.architects.service.ItemService;
import com.architects.service.converter.PageInfo2PagingGridResultConverter;
import com.architects.utils.DesensitizationUtil;
import com.architects.vo.comment.CommentLevelCountVO;
import com.architects.vo.comment.ItemCommentVO;
import com.architects.vo.common.PagingGridVO;
import com.architects.vo.shopcart.ShopCartVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ItmeServiceImpl
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2021/3/21 0021
 * @Version V1.0
 **/
@Service
public class ItemServiceImpl implements ItemService {


    private final ItemsMapper itemsMapper;
    private final ItemsImgMapper itemsImgMapper;
    private final ItemsSpecMapper itemsSpecMapper;
    private final ItemsParamMapper itemsParamMapper;
    private final ItemsCommentsMapper itemsCommentsMapper;

    @Autowired
    public ItemServiceImpl(ItemsMapper itemsMapper, ItemsImgMapper itemsImgMapper, ItemsSpecMapper itemsSpecMapper, ItemsParamMapper itemsParamMapper, ItemsCommentsMapper itemsCommentsMapper) {
        this.itemsMapper = itemsMapper;
        this.itemsImgMapper = itemsImgMapper;
        this.itemsSpecMapper = itemsSpecMapper;
        this.itemsParamMapper = itemsParamMapper;
        this.itemsCommentsMapper = itemsCommentsMapper;
    }



    @Override
    public Items queryItemById(String itemId) {
        return itemsMapper.selectByPrimaryKey(itemId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsImg> queryItemImageList(String itemId) {

        ItemsImg itemsImg = new ItemsImg();
        itemsImg.setItemId(itemId);

        return itemsImgMapper.select(itemsImg);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        ItemsSpec itemsSpec = new ItemsSpec();
        itemsSpec.setItemId(itemId);

        return itemsSpecMapper.select(itemsSpec);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ItemsParam queryItemParam(String itemId) {
        Example example = new Example(ItemsParam.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId",itemId);


        return itemsParamMapper.selectOneByExample(example);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PagingGridVO queryItem(Integer categoryId, String sort, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("categoryId", categoryId);
        map.put("sort", sort);

        PageHelper.startPage(page, pageSize);
        return PageInfo2PagingGridResultConverter.convert(itemsMapper.selectItemByThirdCategory(map), page);
    }

    /**
     * 根据规格ids查询最新的购物车中的商品数据（用于刷新渲染购物车中的商品数据）
     */
    @Override
    public List<ShopCartVO> queryItemsBySpecIds(String specIds) {

        String[] idArr = specIds.split(",");
        List<String> specIdList = Lists.newArrayList(idArr);

        return itemsMapper.selectItemBySpecIdList(specIdList);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PagingGridVO queryItem(String keyword, String sort, Integer page, Integer pageSize) {

        Map<String, Object> map = new HashMap<>();
        map.put("keyword", keyword);
        map.put("sort", sort);

        PageHelper.startPage(page, pageSize);
        return PageInfo2PagingGridResultConverter.convert(itemsMapper.selectItem(map), page);

    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PagingGridVO queryPagingComment(String itemId, Integer level, Integer page, Integer pageSize) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("itemId",itemId);
        map.put("level",level);

        PageHelper.startPage(page,pageSize);
        List<ItemCommentVO> itemCommentVOS = itemsCommentsMapper.selectItemComment(map);

        //信息脱敏
        for (ItemCommentVO itemCommentVO : itemCommentVOS) {
            itemCommentVO.setNickname(DesensitizationUtil.commonDisplay(itemCommentVO.getNickname()));
        }

        return PageInfo2PagingGridResultConverter.convert(itemCommentVOS,page);
    }

    @Override
    public CommentLevelCountVO queryCommentCount(String itemId) {
        int goodCount = selectCommentCount(itemId, CommentLevelEnum.GOOD.getType());
        int normalCount = selectCommentCount(itemId, CommentLevelEnum.NORMAL.getType());
        int badCount = selectCommentCount(itemId, CommentLevelEnum.BAD.getType());

        return CommentLevelCountVO.builder()
                .totalCounts(goodCount + normalCount + badCount)
                .goodCounts(goodCount)
                .normalCounts(normalCount)
                .badCounts(badCount)
                .build();
    }

    /**
     * 查询评价数量
     *
     * @param itemId 商品Id
     * @param level 评价等级
     * @return 评价数
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    int selectCommentCount(String itemId, int level) {
        return itemsCommentsMapper.countByItemIdLevel(itemId, level);
    }

    @Override
    public ItemsSpec queryItemSpecById(String specId) {
        return null;
    }

    @Override
    public ItemsImg queryItemMainImageById(String itemId) {
        return null;
    }

    @Override
    public void decreaseItemSpecStock(String specId, Integer buyCount) {

    }
}
