package com.architects.service.impl;

import com.architects.mapper.CategoryMapper;
import com.architects.pojo.Category;
import com.architects.service.CategoryService;
import com.architects.vo.CategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @ClassName CarouselServiceImpl
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2021/3/20 0020
 * @Version V1.0
 **/
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> queryAllRootLevel() {

        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type","1");
        return categoryMapper.selectByExample(example);
    }

    @Override
    public List<CategoryVO> selectByRootCategoryId(Integer rootCategoryId){
        return categoryMapper.selectByRootCategoryId(rootCategoryId);
    }
}
