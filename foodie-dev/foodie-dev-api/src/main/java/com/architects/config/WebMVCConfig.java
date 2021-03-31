package com.architects.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName WebMVCConfig
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2021/3/31 0031
 * @Version V1.0
 **/
@Component
public class WebMVCConfig implements WebMvcConfigurer {

    @Bean
    public RestTemplate build(RestTemplateBuilder builder){
        return builder.build();
    }



}
