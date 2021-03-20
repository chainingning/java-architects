package com.architects.controller;

import com.architects.pojo.Carousel;
import com.architects.pojo.Category;
import com.architects.service.CarouselService;
import com.architects.service.CategoryService;
import com.architects.utils.JSONVO;
import com.architects.vo.CategoryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/carousel")
    @ApiOperation(value = "获取首页轮播图",notes = "",httpMethod = "GET")
    public JSONVO carousel(){
        List<Carousel> carousels = service.queryAll(1);
        return JSONVO.ok(carousels);
    }


    @GetMapping("/cats")
    @ApiOperation(value = "商品一级分类",notes = "商品一级分类",httpMethod = "GET")
    public JSONVO categories(){
        List<Category> result = categoryService.queryAllRootLevel();
        return JSONVO.ok(result);
    }

    @GetMapping("/subCat/{rootCatId}")
    @ApiOperation(value = "商品二级分类",notes = "商品二级分类",httpMethod = "GET")
    public JSONVO subCategories(
            @ApiParam(name = "rootCatId",value = "一级分类id",required = true)
            @PathVariable Integer rootCatId
    ){
        List<CategoryVO> categoryVOS = categoryService.selectByRootCategoryId(rootCatId);
        return JSONVO.ok(categoryVOS);
    }
}
