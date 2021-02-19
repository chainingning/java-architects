package com.architects.service.impl;

import com.architects.mapper.UsersMapper;
import com.architects.pojo.Users;
import com.architects.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName userServiceImpl
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2021/2/19 0019
 * @Version V1.0
 **/
@Service
public class userServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;


    @Override
    public boolean queryUsername(String username) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", username);

        Users result = usersMapper.selectOneByExample(example);

        return result == null ? false : true;
    }
}
