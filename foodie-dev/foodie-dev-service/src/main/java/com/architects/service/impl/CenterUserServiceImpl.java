package com.architects.service.impl;

import com.architects.bo.CenterUserBO;
import com.architects.mapper.UsersMapper;
import com.architects.pojo.Users;
import com.architects.service.CenterUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class CenterUserServiceImpl implements CenterUserService {

    private final UsersMapper userMapper;

    @Autowired
    public CenterUserServiceImpl(UsersMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Users queryUserInfo(String userId) {
        Users user = userMapper.selectByPrimaryKey(userId);
        user.setPassword(null);

        return user;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Users updateUserInfo(String userId, CenterUserBO centerUserBO) {
        Users updateUser = new Users();
        BeanUtils.copyProperties(centerUserBO, updateUser);
        updateUser.setId(userId);
        updateUser.setUpdatedTime(new Date());

        userMapper.updateByPrimaryKeySelective(updateUser);

        return queryUserInfo(userId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Users updateUserFace(String userId, String faceUrl) {
        Users user = Users.builder()
                .id(userId)
                .face(faceUrl)
                .updatedTime(new Date())
                .build();
        userMapper.updateByPrimaryKeySelective(user);

        return queryUserInfo(userId);
    }
}