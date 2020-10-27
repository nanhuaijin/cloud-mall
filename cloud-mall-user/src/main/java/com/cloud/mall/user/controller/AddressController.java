package com.cloud.mall.user.controller;


import com.cloud.mall.user.entity.AddressDO;
import com.cloud.mall.user.service.AddressService;
import com.cloud.mall.common.result.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 收货地址表 前端控制器
 * </p>
 *
 * @author breeze
 * @since 2020-10-27
 */
@RestController
@Slf4j
@RequestMapping("/user/address")
@Api(tags = "收货地址Controller")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @ApiOperation("保存收货地址")
    @PostMapping("/save/address")
    public BaseResponse saveAddress(
            @ApiParam(name = "addressDO", value = "收货地址对象", required = true)
            @RequestBody AddressDO addressDO) {

        this.addressService.saveAddress(addressDO);
        return BaseResponse.success().message("保存收货地址成功");
    }


}


