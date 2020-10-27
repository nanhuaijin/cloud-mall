package com.cloud.mall.user.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : breeze
 * @date : 2020/10/16
 * @description : 调取微信登录接口认证参数
 */
@ApiModel("调取微信登录接口认证参数")
@Data
@ConfigurationProperties(prefix = "wx")
public class WXLoginProperties {

    @ApiModelProperty(value = "小程序appId")
    private String appId;

    @ApiModelProperty(value = "小程序密钥")
    private String secret;

    @ApiModelProperty(value = "授权类型，此处只需填写 authorization_code")
    private String grantType;
}

