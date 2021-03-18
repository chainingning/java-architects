package com.architects.service.impl;

import com.architects.bo.UserBO;
import com.architects.enums.Sex;
import com.architects.idworker.Sid;
import com.architects.mapper.UsersMapper;
import com.architects.pojo.Users;
import com.architects.service.UserService;
import com.architects.utils.DateUtil;
import com.architects.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * @ClassName userServiceImpl
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2021/2/19 0019
 * @Version V1.0
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsername(String username) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("username", username);

        Users result = usersMapper.selectOneByExample(example);

        return result == null ? false : true;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users createUser(UserBO userBO) {

        Users user = Users.builder()
                .createdTime(new Date())
                .id(Sid.next())
                .password(userBO.getPassword())
                .username(userBO.getUsername())
                .sex(Sex.secret.type)
                .birthday(DateUtil.stringToDate("1900-01-01"))
                .build();
        try {
            user.setPassword(MD5Util.getMD5Str(userBO.getPassword()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        usersMapper.insert(user);

        //返回出去的目的是为了页面显示
        return user;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserForLogin(String username, String password) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",username);
        criteria.andEqualTo("password",password);

        Users users = usersMapper.selectOneByExample(example);

        return users;
    }


}
