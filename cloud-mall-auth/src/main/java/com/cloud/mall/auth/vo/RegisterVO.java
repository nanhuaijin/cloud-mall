package com.cloud.mall.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : breeze
 * @date : 2020/10/17
 * @description : 通过手机号码注册VO对象
 */
@ApiModel("通过手机号码注册VO对象")
@Data
public class RegisterVO {

    @ApiModelProperty("微信openid")
    private String openid;

    @ApiModelProperty("手机号码")
    private String phone;

    @ApiModelProperty("验证码")
    private String code;
}
