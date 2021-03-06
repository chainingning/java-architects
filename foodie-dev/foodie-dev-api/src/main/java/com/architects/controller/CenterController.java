package com.architects.controller;

import com.architects.pojo.Users;
import com.architects.service.CenterUserService;
import com.architects.utils.JSONVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 个人中心

 */
@Api(value = "center - 用户中心", tags = {"用户中心展示的相关接口"})
@RestController
@RequestMapping("center")
public class CenterController {

    private final CenterUserService centerUserService;

    @Autowired
    public CenterController(CenterUserService centerUserService) {
        this.centerUserService = centerUserService;
    }

    @ApiOperation(value = "获取用户信息", notes = "获取用户信息", httpMethod = "GET")
    @GetMapping("/userInfo")
    public JSONVO userInfo(@ApiParam(name = "userId", value = "用户id", required = true)
                           @RequestParam String userId) {
        Users user = centerUserService.queryUserInfo(userId);
        return JSONVO.ok(user);
    }
}