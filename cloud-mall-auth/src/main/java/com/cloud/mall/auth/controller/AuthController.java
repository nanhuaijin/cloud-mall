package com.cloud.mall.auth.controller;

import com.cloud.mall.auth.service.AuthService;
import com.cloud.mall.common.result.BaseResponse;
import com.cloud.mall.common.result.ResultCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : breeze
 * @date : 2020/10/15
 * @description :
 */
@RestController
@Logger
@Api(tags = "登录认证Controller")
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @ApiOperation("登录认证")
    @PostMapping("/accredit")
    public BaseResponse authentication(
            @ApiParam(name = "username", value = "账号", required = true)
            @RequestParam("username") String username,
            @ApiParam(name = "password", value = "密码", required = true)
            @RequestParam("password") String password) {

        boolean flag = this.authService.authentication(username, password);
        if (flag) {
            return BaseResponse.setResult(ResultCodeEnum.ACCOUNT_LOGIN_SUCCESS);
        } else {
            return BaseResponse.setResult(ResultCodeEnum.ACCOUNT_OR_PASSWORD_ERROR);
        }
    }
}
