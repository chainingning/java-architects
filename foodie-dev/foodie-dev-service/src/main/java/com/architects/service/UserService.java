package com.architects.service;

import com.architects.bo.UserBO;
import com.architects.pojo.Users;

import java.security.NoSuchAlgorithmException;

/**
 * @ClassName UserService
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2021/2/19 0019
 * @Version V1.0
 **/
public interface UserService {

    /**
     * 判断用户名是否存在
     * @param username
     * @return
     */
    boolean queryUsername(String username);

    /**
     * 创建用户
     * @param userBO
     * @return
     */
    Users createUser(UserBO userBO);

}
