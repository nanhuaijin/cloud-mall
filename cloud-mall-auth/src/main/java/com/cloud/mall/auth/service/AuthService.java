package com.cloud.mall.auth.service;

/**
 * @author : breeze
 * @date : 2020/10/15
 * @description :
 */
public interface AuthService {
    /**
     * 登录认证
     * @param username
     * @param password
     * @return
     */
    boolean authentication(String username, String password);
}
