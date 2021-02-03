package com.architects;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @ClassName Application
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2020/12/31 0031
 * @Version V1.0
 **/
@SpringBootApplication
@MapperScan(basePackages = "com.architects.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
