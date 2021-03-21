package com.architects.service;

import com.architects.pojo.Items;
import com.architects.pojo.ItemsImg;
import com.architects.pojo.ItemsParam;
import com.architects.pojo.ItemsSpec;
import com.architects.vo.comment.CommentLevelCountVO;
import com.architects.vo.common.PagingGridVO;
import com.architects.vo.shopcart.ShopCartVO;

import java.util.List;

/**
 * 商品
 *
 * @author yangxin
 * 2019/11/27 21:53
 */
public interface ItemService {
    /**
     * 根据商品Id查询详情
     *
     * @param itemId 商品Id
     * @return 商品
     */
    Items queryItemById(String itemId);

    /**
     * 根据商品Id查询商品图片列表
     *
     * @param itemId 商品Id
     * @return 商品图片
     */
    List<ItemsImg> queryItemImageList(String itemId);

    /**
     * 根据商品Id查询商品规格
     *
     * @param itemId 商品Id
     * @return 商品规格
     */
    List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 根据商品Id查询商品参数
     *
     * @param itemId 商品Id
     * @return 商品参数
     */
    ItemsParam queryItemParam(String itemId);

    /**
     * 根据商品Id查询商品评价
     *
     * @param itemId 商品Id
     * @return 商品评价
     */
    CommentLevelCountVO queryCommentCount(String itemId);

    /**
     * 根据商品Id查询商品的评价（分页）
     */
    PagingGridVO queryPagingComment(String itemId, Integer level, Integer page, Integer pageSize);

    /**
     * 搜索商品列表
     *
     * @param keyword 关键字
     * @param sort 排序字段
     * @param page 当前页
     * @param pageSize 每页显示的记录数
     */
    PagingGridVO queryItem(String keyword, String sort, Integer page, Integer pageSize);

    /**
     * 搜索商品列表
     */
    PagingGridVO queryItem(Integer categoryId, String sort, Integer page, Integer pageSize);

    /**
     * 根据规格ids查询最新的购物车中的商品数据（用于刷新渲染购物车中的商品数据）
     */
    List<ShopCartVO> queryItemsBySpecIds(String specIds);

    /**
     * 根据商品规格Id，获取规格对象的具体信息
     *
     * @param specId 商品规格Id
     * @return 规格对象
     */
    ItemsSpec queryItemSpecById(String specId);

    /**
     * 根据商品Id，获得商品图片主图url
     *
     * @param itemId 商品Id
     * @return 商品图片主图url
     */
    ItemsImg queryItemMainImageById(String itemId);

    /**
     * 减少库存
     *
     * @param specId 商品规格Id
     * @param buyCount 购买数量
     */
    void decreaseItemSpecStock(String specId, Integer buyCount);
}
