package com.architects.config;

import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
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

    @Bean
    public CorsFilter corsFilter(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.addAllowedMethod("*");

        corsConfiguration.addAllowedOrigin("http://localhost:8080");

        corsConfiguration.addAllowedHeader("*");

        //设置是否发送cookie信息
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
        corsSource.registerCorsConfiguration("/**",corsConfiguration);

        return new CorsFilter(corsSource);
    }
}
