package com.architects.controller;

import com.architects.bo.UserBO;
import com.architects.pojo.Users;
import com.architects.service.UserService;
import com.architects.utils.CookieUtil;
import com.architects.utils.JSONUtil;
import com.architects.utils.JSONVO;
import com.architects.utils.MD5Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;

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

    @ApiOperation(value = "判断用户是否存在",notes ="判断用户是否存在",httpMethod = "GET")
    @GetMapping("/login")
    public JSONVO login(@Valid @RequestBody UserBO userBO, HttpServletRequest req, HttpServletResponse res) throws NoSuchAlgorithmException {

        if (StringUtils.isBlank(userBO.getUsername())) {
            return JSONVO.errorMsg("用户名不能为空");
        }

        Users users = userService.queryUserForLogin(userBO.getUsername(),
                MD5Util.getMD5Str(userBO.getPassword())
        );

        if (users == null) {
            return JSONVO.errorMsg("用户或密码不正确");
        }

        CookieUtil.setCookie(req,res,"user", JSONUtil.obj2String(users),true);

        return JSONVO.ok();
    }
}
