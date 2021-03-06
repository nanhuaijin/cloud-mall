package com.cloud.mall.common.consts;

/**
 * @author : breeze
 * @date : 2020/9/1
 * @description : 项目常量
 */
public interface AppConst {

    /**
     * 微信小程序授权登录url地址
     */
    String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=";
    /**
     * redis中存储验证码的前缀
     */
    String SMS_CODE_PREFIX = "sms:code:";
    /**
     * redis中存储验证码的次数
     */
    String SMS_CODE_COUNT_PREFIX = "sms:code:count:";
    /**
     * 每个手机每天最多发送3条短信
     */
    Integer SMS_CODE_MAX_COUNT = 3;
    /**
     * 防止表单重复提交token前缀
     */
    String UPDATE_TOKEN = "update:token:";
    /**
     * 每天可更新密码次数前缀
     */
    String UPDATE_PASSWORD_EVERY_DAY = "update:password:count";
    /**
     * 更新密码的频率 单位天
     */
    Integer UPDATE_PASSWORD_EXPIRE_TIME = 1;

    /**
     * 注册用户默认头像地址
     */
    String DEFAULT_AVATAR_URL = "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif";
}
