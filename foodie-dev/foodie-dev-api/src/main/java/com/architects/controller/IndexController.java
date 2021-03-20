package com.architects.controller;

import com.architects.pojo.Carousel;
import com.architects.service.CarouselService;
import com.architects.utils.JSONVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName HelloController
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2020/12/31 0031
 * @Version V1.0
 **/
@Api(value = "首页相",tags = {"首页展示相关接口"})
@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private CarouselService service;

    @GetMapping("/carousel")
    @ApiOperation(value = "获取首页轮播图",notes = "",httpMethod = "GET")
    public JSONVO carousel(){
        List<Carousel> carousels = service.queryAll(1);
        return JSONVO.ok(carousels);
    }
}
