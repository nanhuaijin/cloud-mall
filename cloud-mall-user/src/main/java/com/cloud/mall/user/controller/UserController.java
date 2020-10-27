package com.cloud.mall.user.controller;

import com.cloud.mall.user.entity.UserDO;
import com.cloud.mall.user.service.UserService;
import com.cloud.mall.user.vo.AuthVO;
import com.cloud.mall.user.vo.RegisterVO;
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
@RequestMapping("/user/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("登录认证")
    @PostMapping("/accredit")
    public BaseResponse authentication(
            @ApiParam(name = "username", value = "账号", required = true)
            @RequestBody AuthVO authVO) {

        UserDO userDO = this.userService.authentication(authVO);
        return BaseResponse.setResult(ResultCodeEnum.ACCOUNT_LOGIN_SUCCESS).data("user", userDO);
    }

    @ApiOperation("发送手机验证码")
    @GetMapping("/sms")
    public BaseResponse sendSMS(
            @ApiParam(name = "phone", value = "手机号码", required = true)
            @RequestParam("phone") String phone,
            @ApiParam(name = "type", value = "类型 0-绑定手机 1-解绑手机", required = true)
            @RequestParam("type") int type) {

        this.userService.sendSms(phone, type);
        return BaseResponse.setResult(ResultCodeEnum.SEND_SMS_CODE_SUCCESS);
    }

    @ApiOperation("注册账号 - 绑定手机号")
    @PostMapping("/register")
    public BaseResponse register(
            @ApiParam(name = "registerVO", value = "注册VO对象", required = true)
            @RequestBody RegisterVO registerVO) {

        UserDO userDO = this.userService.register(registerVO);
        return BaseResponse.setResult(ResultCodeEnum.ACCOUNT_REGISTER_SUCCESS).data("user", userDO);
    }

    @ApiOperation("解绑手机号码")
    @PostMapping("/untie")
    public BaseResponse untiePhone(
            @ApiParam(name = "registerVO", value = "注册VO对象", required = true)
            @RequestBody RegisterVO registerVO) {

        this.userService.untiePhone(registerVO);
        return BaseResponse.setResult(ResultCodeEnum.UNTIE_PHONE_SUCCESS);
    }

    @ApiOperation("更新生日")
    @GetMapping("/birthday/{id}")
    public BaseResponse updateBirthday(
            @ApiParam(name = "birthday", value = "生日", required = true)
            @RequestParam("birthday") String birthday,
            @ApiParam(name = "id", value = "当前用户id", required = true)
            @PathVariable("id") Integer id) {

        this.userService.updateBirthday(birthday, id);
        return BaseResponse.success().message("更新生日成功");
    }
}
