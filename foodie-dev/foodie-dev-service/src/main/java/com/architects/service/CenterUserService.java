package com.architects.service;

import com.architects.bo.CenterUserBO;
import com.architects.pojo.Users;

/**
 * 个人中心用户
 *
 * @author yangxin
 * 2019/11/25 14:32
 */
public interface CenterUserService {

    /**
     * 根据用户Id查询用户信息
     *
     * @param userId 用户Id
     * @return 用户信息
     */
    Users queryUserInfo(String userId);

    /**
     * 修改用户信息
     */
    Users updateUserInfo(String userId, CenterUserBO centerUserBO);

    /**
     * 用户头像更新
     *
     * @param userId 用户id
     * @param faceUrl 头像url
     */
    Users updateUserFace(String userId, String faceUrl);
}