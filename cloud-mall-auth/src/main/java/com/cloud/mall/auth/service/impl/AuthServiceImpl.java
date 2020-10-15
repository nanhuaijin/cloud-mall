package com.cloud.mall.auth.service.impl;

import com.cloud.mall.auth.service.AuthService;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.springframework.stereotype.Service;

/**
 * @author : breeze
 * @date : 2020/10/15
 * @description :
 */
@Service
@Logger
public class AuthServiceImpl implements AuthService {

    @Override
    public boolean authentication(String username, String password) {
        if ("123".equals(username) && "123".equals(password)) {
            return true;
        } else {
            return false;
        }
    }
}
