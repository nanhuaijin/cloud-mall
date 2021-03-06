package com.cloud.mall.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author : breeze
 * @date : 2020/10/15
 * @description :
 */
@SpringBootApplication
@EnableDiscoveryClient
@RefreshScope
@MapperScan(basePackages = "com.cloud.mall.user.mapper")
public class UserApp {
    public static void main(String[] args) {
        SpringApplication.run(UserApp.class, args);
    }
}
