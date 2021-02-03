package com.architects.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName HelloController
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2020/12/31 0031
 * @Version V1.0
 **/
//@Controller
@RestController
@RequestMapping("/metric")
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }

    @PostMapping("/test")
    public void test(){
        System.out.println("=========请求成功");
    }
}
