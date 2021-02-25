package com.architects.config;

import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.CorsFilter;

/**
 * @ClassName CorsConfig
 * @Description: 跨域设置
 * @Author ning.chai@foxmail.com
 * @Date 2021/2/23 0023
 * @Version V1.0
 **/
@Configuration
@NoArgsConstructor
public class CorsConfig {

    public CorsFilter corsFilter(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedMethod("*");
//        corsConfiguration.ad
        return null;
    }
}
