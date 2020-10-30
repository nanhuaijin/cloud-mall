package com.cloud.mall.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author : breeze
 * @date : 2020/10/30
 * @description :
 */
@SpringBootApplication
@EnableDiscoveryClient
@RefreshScope
public class OssApp {
    public static void main(String[] args) {
        SpringApplication.run(OssApp.class, args);
    }
}
