package com.cloud.mall.oss.service.impl;

import cn.hutool.core.date.DateTime;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.cloud.mall.common.annotation.MallLog;
import com.cloud.mall.oss.config.OssProperties;
import com.cloud.mall.oss.service.OssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

/**
 * @author : breeze
 * @date : 2020/10/30
 * @description :
 */
@Service
@Slf4j
@EnableConfigurationProperties(OssProperties.class)
public class OssServiceImpl implements OssService {

    @Autowired
    private OssProperties ossProperties;

    @MallLog
    @Override
    public String uploadFile(String module, InputStream inputStream, String originalFilename) {

        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ossProperties.getEndpoint();
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = ossProperties.getAccessKeyId();
        String accessKeySecret = ossProperties.getAccessSecret();
        String bucketName = ossProperties.getBucketName();

        //判断oss实例是否存在：如果不存在则创建，如果存在则获取
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        if (!ossClient.doesBucketExist(bucketName)) {
            //创建bucket
            ossClient.createBucket(bucketName);
            //设置oss实例的访问权限：公共读
            ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
        }

        //构建日期路径：avatar/2019/02/26/文件名
        String folder = new DateTime().toString("yyyy/MM/dd");

        //文件名：uuid.扩展名
        String fileName = UUID.randomUUID().toString();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String key = new StringBuffer()
                .append(module)
                .append("/")
                .append(folder)
                .append("/")
                .append(fileName)
                .append(fileExtension)
                .toString();

        //文件上传至阿里云
        ossClient.putObject(bucketName, key, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();

        //返回url地址
        return new StringBuffer()
                .append("https://")
                .append(bucketName)
                .append(".")
                .append(endpoint)
                .append("/")
                .append(key)
                .toString();
    }

    @MallLog
    @Override
    public void removeFile(String url) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ossProperties.getEndpoint();
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = ossProperties.getAccessKeyId();
        String accessKeySecret = ossProperties.getAccessSecret();
        String bucketName = ossProperties.getBucketName();

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        String host = new StringBuffer()
                .append("https://")
                .append(bucketName)
                .append(".")
                .append(endpoint)
                .append("/").toString();
        String objectName = url.substring(host.length());
        // 删除文件。
        ossClient.deleteObject(bucketName, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
