package com.cloud.mall.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.mall.auth.entity.UserDO;
import com.cloud.mall.auth.vo.AuthVO;

/**
 * @author : breeze
 * @date : 2020/10/15
 * @description :
 */
public interface AuthService extends IService<UserDO> {
    /**
     * 登录认证
     * @param authVO
     * @return
     */
    UserDO authentication(AuthVO authVO);
}
