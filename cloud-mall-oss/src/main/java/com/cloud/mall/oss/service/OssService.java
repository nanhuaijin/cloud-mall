package com.cloud.mall.oss.service;

import java.io.InputStream;

/**
 * @author : breeze
 * @date : 2020/10/30
 * @description :
 */
public interface OssService {
    /**
     * 上传文件到OSS
     * @param module 具体模块名字
     * @param inputStream 文件输入流
     * @param originalFilename 文件原始名字
     * @return 返回url
     */
    String uploadFile(String module, InputStream inputStream, String originalFilename);

    /**
     * 删除OSS文件
     * @param url
     */
    void removeFile(String url);
}
