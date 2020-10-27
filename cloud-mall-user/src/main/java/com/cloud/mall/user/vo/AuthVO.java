package com.cloud.mall.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : breeze
 * @date : 2020/10/16
 * @description : 登录认证DO对象
 */
@ApiModel("登录认证DO对象")
@Data
public class AuthVO {

    @ApiModelProperty(value = "验证微信登录的code，前端传递")
    private String code;

    @ApiModelProperty(value = "微信昵称")
    private String nickName;

    @ApiModelProperty(value = "性别 0-女 1-男")
    private Integer gender;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "省份")
    private String province;

    @ApiModelProperty(value = "国家")
    private String country;

    @ApiModelProperty(value = "头像地址")
    private String avatarUrl;
}
