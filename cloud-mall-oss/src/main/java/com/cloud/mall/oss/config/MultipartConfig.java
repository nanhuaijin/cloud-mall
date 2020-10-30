package com.cloud.mall.oss.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

/**
 * @author : breeze
 * @date : 2020/10/30
 * @description : Multipart配置文件
 */
@Configuration
public class MultipartConfig {

    /**
     * 配置上传文件大小的配置
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小5M
        factory.setMaxFileSize(DataSize.ofMegabytes(5));
        /// 总上传数据大小5M
        factory.setMaxRequestSize(DataSize.ofMegabytes(5));
        return factory.createMultipartConfig();
    }
}
