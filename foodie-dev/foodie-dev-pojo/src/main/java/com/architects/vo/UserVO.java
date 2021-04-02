package com.architects.vo;

import lombok.Data;

@Data
public class UserVO {

    /**
     * Id
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 头像
     */
    private String face;

    /**
     * 性别
     */
    private Integer sex;
}