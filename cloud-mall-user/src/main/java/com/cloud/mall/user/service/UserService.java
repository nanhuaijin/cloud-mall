package com.cloud.mall.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.mall.user.entity.UserDO;
import com.cloud.mall.user.vo.AuthVO;
import com.cloud.mall.user.vo.RegisterVO;

/**
 * @author : breeze
 * @date : 2020/10/15
 * @description :
 */
public interface UserService extends IService<UserDO> {
    /**
     * 登录认证
     * @param authVO
     * @return
     */
    UserDO authentication(AuthVO authVO);

    /**
     * 发送手机验证码
     * @param phone 手机号码
     * @param type 0-绑定手机号 1-解绑手机号
     */
    void sendSms(String phone, int type);

    /**
     * 注册账号 - 绑定手机号码
     * @param registerVO
     */
    UserDO register(RegisterVO registerVO);

    /**
     * 解除绑定手机号码
     * @param registerVO
     */
    void untiePhone(RegisterVO registerVO);

    /**
     * 更新生日
     * @param birthday 生日
     * @param id 当前用户id
     */
    void updateBirthday(String birthday, Integer id);
}
