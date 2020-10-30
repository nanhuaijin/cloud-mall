package com.cloud.mall.oss.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : breeze
 * @date : 2020/10/30
 * @description : 阿里云OSS配置类
 */
@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class OssProperties {

    private String endpoint;
    private String accessKeyId;
    private String accessSecret;
    private String bucketName;
}
