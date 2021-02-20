package com.architects.controller;

import com.architects.bo.UserBO;
import com.architects.service.UserService;
import com.architects.utils.JSONVO;
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
@RestController
@RequestMapping("passport")
public class PassportController {

    @Autowired
    private UserService userService;


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

    @PostMapping("/register")
    public JSONVO register(@Valid @RequestBody UserBO userBO){
        userService.createUser(userBO);
        return JSONVO.ok();
    }
}
