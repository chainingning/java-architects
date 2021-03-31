package com.architects.controller;

import com.architects.utils.JSONVO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName HelloController
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2020/12/31 0031
 * @Version V1.0
 **/
//@Controller
@Api(value = "地址相关",tags = {"地址相关操作"})
@RestController
@RequestMapping("/addr")
public class AddressController {

    public JSONVO list(){
        return null;
    }

}
