package com.chaining;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @ClassName ScriptApplication
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2020/12/28 0028
 * @Version V1.0
 **/
@SpringBootApplication
@EnableAsync
public class ScriptApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScriptApplication.class,args);
    }
}
