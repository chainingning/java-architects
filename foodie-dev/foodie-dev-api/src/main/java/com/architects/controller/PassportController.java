package com.architects.controller;

import com.architects.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public int usernameIsExist(@RequestParam String username){

        if (StringUtils.isBlank(username)) {
            return 500;
        }



        boolean isExist = userService.queryUsername(username);

        if (isExist) {
            return 500;
        }

        return  200;
    }
}
