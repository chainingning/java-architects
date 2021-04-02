package com.architects.controller;

import com.architects.bo.CenterUserBO;
import com.architects.pojo.Users;
import com.architects.service.CenterUserService;
import com.architects.utils.CookieUtil;
import com.architects.utils.JSONUtil;
import com.architects.utils.JSONVO;
import com.architects.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName CenterUserController
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2021/4/1 0001
 * @Version V1.0
 **/
@Api(value = "用户信息接口",tags = {"用户信息相关接口"})
@RequestMapping("/userInfo")
@RestController
public class CenterUserController {
    @Autowired
    private  CenterUserService centerUserService;


    @ApiOperation(value = "修改用户信息", notes = "修改用户信息", httpMethod = "POST")
    @PostMapping("/update")
    public JSONVO update(@ApiParam(name = "userId", value = "用户id", required = true)
                         @RequestParam String userId,
                         @RequestBody @Valid CenterUserBO centerUserBO,
                         HttpServletRequest request,
                         HttpServletResponse response,
                         BindingResult bindingResult) {
        // 判断BindingResult是否保存错误的验证信息，如果有，则直接return
        if (bindingResult.hasErrors()) {
            return JSONVO.errorMap(getErrorMap(bindingResult));
        }

        Users user = centerUserService.updateUserInfo(userId, centerUserBO);

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);

        CookieUtil.setCookie(request, response, "user", JSONUtil.obj2String(userVO), true);

        // todo 后续要改，增加令牌token，会整合进redis，分布式会话

        return JSONVO.ok();
    }

    private Map<String, String> getErrorMap(BindingResult bindingResult) {
        Map<String, String> map = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            // 发生验证错误所对应的某一个属性
            String field = fieldError.getField();
            // 验证错误的信息
            String defaultMessage = fieldError.getDefaultMessage();

            map.put(field, defaultMessage);
        }

        return map;
    }

}