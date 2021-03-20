package com.architects.mapper;

import com.architects.my.mapper.MyMapper;
import com.architects.pojo.Category;
import com.architects.vo.CategoryVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


public interface CategoryMapper extends MyMapper<Category> {

    List<CategoryVO> selectByRootCategoryId(Integer rootCategoryId);

}