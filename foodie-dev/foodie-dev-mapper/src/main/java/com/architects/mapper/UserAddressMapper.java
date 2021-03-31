package com.architects.mapper;

import com.architects.my.mapper.MyMapper;
import com.architects.pojo.UserAddress;

import java.util.List;

public interface UserAddressMapper extends MyMapper<UserAddress> {
    List<UserAddress> selectByUserId(String userId);
}