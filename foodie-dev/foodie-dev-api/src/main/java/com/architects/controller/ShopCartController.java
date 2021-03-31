package com.architects.controller;

import com.architects.bo.ShopCartBO;
import com.architects.enums.ResultEnum;
import com.architects.utils.JSONVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName ShopCartController
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2021/3/21 0021
 * @Version V1.0
 **/
@Api(value = "购物车接口Controller", tags = {"购物车接口相关的API"})
@RequestMapping("/shopcart")
@RestController
@Slf4j
public class ShopCartController {


    @ApiOperation(value = "添加商品到购物车", notes = "添加商品到购物车", httpMethod = "POST")
    @PostMapping("/add")
    public JSONVO add(@RequestParam String userId,
                      @RequestBody ShopCartBO shopCartBO,
                      HttpServletRequest httpServletRequest,
                      HttpServletResponse httpServletResponse) {
        if (StringUtils.isEmpty(userId)) {
            return JSONVO.errorMsg("");
        }

        log.info("shopCartQuery: [{}]", shopCartBO);

        // todo 前端用户在登录的情况下，添加商品到购物车，会同时在后端同步购物车到redis缓存

        return JSONVO.ok();
    }


    @ApiOperation(value = "从购物车中删除商品", notes = "从购物车中删除商品", httpMethod = "POST")
    @PostMapping("/del")
    public JSONVO delete(@RequestParam String userId,
                         @RequestParam String itemSpecId) {
        log.info("userId: [{}], itemSpecId: [{}]", userId, itemSpecId);

        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(itemSpecId)) {
            return JSONVO.errorMsg(ResultEnum.PARAMETER_CANT_EMPTY.getMessage());
        }

        // todo 用户在页面删除购物车中的商品数据，如果此时用户已经登录，则需要同步删除后端购物车项

        return JSONVO.ok();
    }


}
