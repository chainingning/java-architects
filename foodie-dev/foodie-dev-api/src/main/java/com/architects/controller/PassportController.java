package com.architects.controller;

import com.architects.bo.UserBO;
import com.architects.service.UserService;
import com.architects.utils.JSONVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @ClassName PassportController
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2021/2/19 0019
 * @Version V1.0
 **/
@Api(value = "注册登录",tags = {"用于注册登录的相关接口"})
@RestController
@RequestMapping("passport")
public class PassportController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "判断用户是否存在",notes ="判断用户是否存在",httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public JSONVO usernameIsExist(@RequestParam String username){

        if (StringUtils.isBlank(username)) {
            return JSONVO.errorMsg("用户名不能为空");
        }

        boolean isExist = userService.queryUsername(username);

        if (isExist) {
            return JSONVO.errorMsg("用户名已存在");
        }

        return JSONVO.ok();
    }


    @ApiOperation(value = "用户注册",notes ="用户注册",httpMethod = "POST")
    @PostMapping("/register")
    public JSONVO register(@Valid @RequestBody UserBO userBO){
        userService.createUser(userBO);
        return JSONVO.ok();
    }
}
