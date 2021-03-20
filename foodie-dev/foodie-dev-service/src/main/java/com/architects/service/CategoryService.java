package com.architects.service;

import com.architects.pojo.Category;
import com.architects.vo.CategoryVO;

import java.util.List;

/**
 * @ClassName CarouselService
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2021/3/20 0020
 * @Version V1.0
 **/
public interface CategoryService {
    List<Category> queryAllRootLevel();

    List<CategoryVO> selectByRootCategoryId(Integer rootCategoryId);
}
