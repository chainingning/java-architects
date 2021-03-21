package com.architects.controller;

import com.architects.pojo.Items;
import com.architects.pojo.ItemsImg;
import com.architects.pojo.ItemsParam;
import com.architects.pojo.ItemsSpec;
import com.architects.service.ItemService;
import com.architects.utils.JSONVO;
import com.architects.vo.ItemInfoVO;
import com.architects.vo.comment.CommentLevelCountVO;
import com.architects.vo.common.PagingGridVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName ItemsController
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2021/3/21 0021
 * @Version V1.0
 **/
@Api(value = "商品接口", tags = {"商品信息展示相关接口"})
@RestController
@RequestMapping("/items")
public class ItemsController {

    private final ItemService itemService;

    @Autowired
    public ItemsController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * 商品详情
     */
    @ApiOperation(value = "查询商品详情", notes = "查询商品详情", httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public JSONVO info(
            @ApiParam(name = "itemId", value = "商品Id", required = true)
            @PathVariable String itemId) {

        if (StringUtils.isEmpty(itemId)) {
            return JSONVO.errorMsg(null);
        }

        Items item = itemService.queryItemById(itemId);
        List<ItemsImg> itemImageList = itemService.queryItemImageList(itemId);
        List<ItemsSpec> itemSpecList = itemService.queryItemSpecList(itemId);
        ItemsParam itemParam = itemService.queryItemParam(itemId);

        ItemInfoVO itemInfoVO = ItemInfoVO.builder()
                .item(item)
                .itemImgList(itemImageList)
                .itemSpecList(itemSpecList)
                .itemParams(itemParam)
                .build();

        return JSONVO.ok(itemInfoVO);
    }


    /**
     * 商品评论
     */
    @ApiOperation(value = "查询商品评论", notes = "查询商品评论", httpMethod = "GET")
    @GetMapping("/comments")
    public JSONVO comment(
            @ApiParam(name = "itemId", value = "商品Id", required = true)
            @RequestParam String itemId,
            @ApiParam(name = "level", value = "评价等级")
            @RequestParam Integer level,
            @ApiParam(name = "page", value = "查询第几页")
            @RequestParam(defaultValue = "1") Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数")
            @RequestParam(defaultValue = "10") Integer pageSize) {

        if (StringUtils.isEmpty(itemId)) {
            return JSONVO.errorMsg(null);
        }

        PagingGridVO pagingGridVO = itemService.queryPagingComment(itemId, level, page, pageSize);
        return JSONVO.ok(pagingGridVO);
    }



    /**
     * 商品列表
     */
    @ApiOperation(value = "查询商品列表", notes = "查询商品列表", httpMethod = "GET")
    @GetMapping("/search")
    public JSONVO search(
            @ApiParam(name = "keywords", value = "关键字", required = true)
            @RequestParam(name = "keywords") String keyword,
            @ApiParam(name = "sort", value = "排序")
            @RequestParam String sort,
            @ApiParam(name = "page", value = "查询第几页")
            @RequestParam(defaultValue = "1") Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数")
            @RequestParam(defaultValue = "20") Integer pageSize) {

        if (StringUtils.isEmpty(keyword)) {
            return JSONVO.errorMsg(null);
        }

        return JSONVO.ok(itemService.queryItem(keyword, sort, page, pageSize));
    }



}
