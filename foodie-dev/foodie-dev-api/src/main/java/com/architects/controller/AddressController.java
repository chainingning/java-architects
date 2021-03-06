package com.architects.controller;

import com.architects.bo.AddressBO;
import com.architects.enums.ResultEnum;
import com.architects.service.AddressService;
import com.architects.utils.JSONVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
@RequestMapping("/address")
public class AddressController {
    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    /*
      用户在确认订单页面，可以针对收货地址做如下操作：
      1. 查询用户的所有收货地址列表
      2. 新增收货地址
      3. 删除收货地址
      4. 修改收货地址
      5. 设置默认地址
     */

    /**
     * 查询用户的所有收货地址列表
     */
    @ApiOperation(value = "根据用户id查询收货地址列表", notes = "根据用户id查询收货地址列表", httpMethod = "POST")
    @PostMapping("/list")
    public JSONVO list(@RequestParam String userId) {

        if (StringUtils.isEmpty(userId)) {
            return JSONVO.errorMsg("");
        }

        return JSONVO.ok(addressService.queryAll(userId));
    }

    /**
     * 新增收货地址
     */
    @ApiOperation(value = "用户新增地址", notes = "用户新增地址", httpMethod = "POST")
    @PostMapping("/add")
    public JSONVO add(@RequestBody @Valid AddressBO addressBO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            FieldError fieldError = bindingResult.getFieldError();
            return JSONVO.errorMsg(fieldError == null ? "" : fieldError.getDefaultMessage());
        }

        addressService.addNewUserAddress(addressBO);
        return JSONVO.ok();
    }

    /**
     * 修改收货地址
     */
    @ApiOperation(value = "用户修改地址", notes = "用户修改地址", httpMethod = "POST")
    @PostMapping("/update")
    public JSONVO update(@RequestBody @Valid AddressBO addressBO, BindingResult bindingResult) {

        if (StringUtils.isEmpty(addressBO.getAddressId())) {
            return JSONVO.errorMsg(ResultEnum.PARAMETER_ERROR.getMessage());
        }

        if (bindingResult.hasErrors()) {


            FieldError fieldError = bindingResult.getFieldError();
            return JSONVO.errorMsg(fieldError == null ? "" : fieldError.getDefaultMessage());
        }

        addressService.updateUserAddress(addressBO);
        return JSONVO.ok();
    }

    /**
     * 删除收货地址
     */
    @ApiOperation(value = "用户删除地址", notes = "用户删除地址", httpMethod = "POST")
    @PostMapping("/delete")
    public JSONVO delete(@RequestParam String userId, @RequestParam String addressId) {


        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(addressId)) {
            return JSONVO.errorMsg("");
        }

        addressService.deleteUserAddress(addressId);
        return JSONVO.ok();
    }

    /**
     * 设置默认收货地址
     */
    @ApiOperation(value = "用户设置默认地址", notes = "用户设置默认地址", httpMethod = "POST")
    @PostMapping("/setDefalut")
    public JSONVO setDefault(@RequestParam String userId, @RequestParam String addressId) {

        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(addressId)) {
            return JSONVO.errorMsg("");
        }

        addressService.updateUserAddressAsDefault(userId, addressId);
        return JSONVO.ok();
    }

}
