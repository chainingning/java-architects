package com.architects.enums;

/**
 * @ClassName Sex
 * @Description: 性别枚举
 * @Author ning.chai@foxmail.com
 * @Date 2021/2/20 0020
 * @Version V1.0
 **/
public enum  Sex {
    woman(0,"女"),
    man(1,"男"),
    secret(2,"保密");

    public final Integer type;

    public final String value;

    Sex(Integer type, String value) {
        this.type = type;
        this.value = value;
    }


}
