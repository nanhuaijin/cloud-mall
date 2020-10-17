package com.cloud.mall.auth.controller;

import com.cloud.mall.auth.entity.UserDO;
import com.cloud.mall.auth.service.AuthService;
import com.cloud.mall.auth.vo.AuthVO;
import com.cloud.mall.common.result.BaseResponse;
import com.cloud.mall.common.result.ResultCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : breeze
 * @date : 2020/10/15
 * @description :
 */
@RestController
@Slf4j
@Api(tags = "登录认证Controller")
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @ApiOperation("登录认证")
    @PostMapping("/accredit")
    public BaseResponse authentication(
            @ApiParam(name = "username", value = "账号", required = true)
            @RequestBody AuthVO authVO) {

        UserDO userDO = this.authService.authentication(authVO);
        return BaseResponse.setResult(ResultCodeEnum.ACCOUNT_LOGIN_SUCCESS).data("user", userDO);
    }

    @ApiOperation("发送手机验证码")
    @GetMapping("/sms")
    public BaseResponse sendSMS(
            @ApiParam(name = "phone", value = "手机号码", required = true)
            @RequestParam("phone") String phone,
            @ApiParam(name = "type", value = "类型 0-绑定手机 1-解绑手机", required = true)
            @RequestParam("type") int type) {

        this.authService.sendSms(phone, type);
        return BaseResponse.setResult(ResultCodeEnum.SEND_SMS_CODE_SUCCESS);
    }
}
