package com.architects.service;

import com.architects.pojo.Carousel;

import java.util.List;

/**
 * @ClassName CarouselService
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2021/3/20 0020
 * @Version V1.0
 **/
public interface CarouselService {
    List<Carousel> queryAll(Integer isShow);
}
