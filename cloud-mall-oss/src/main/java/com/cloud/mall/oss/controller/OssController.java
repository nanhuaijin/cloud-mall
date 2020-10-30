package com.cloud.mall.oss.controller;

import com.cloud.mall.common.exception.ApplicationException;
import com.cloud.mall.common.result.BaseResponse;
import com.cloud.mall.common.result.ResultCodeEnum;
import com.cloud.mall.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author : breeze
 * @date : 2020/10/30
 * @description :
 */
@RestController
@Slf4j
@Api(tags = "OSS对象存储Controller")
@RequestMapping("/oss")
public class OssController {

    @Autowired
    private OssService ossService;

    @ApiOperation("上传OSS文件")
    @PostMapping("/upload")
    public BaseResponse uploadFile(
            @ApiParam(name = "module", value = "具体模块", required = true)
            @RequestParam("module") String module,
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file) {

        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();

            String url = this.ossService.uploadFile(module, inputStream, originalFilename);
            return BaseResponse.success().message("上传文件成功").data("url", url);
        } catch (IOException e) {
            log.error("上传文件异常-OssController.uploadFile：", e);
            throw new ApplicationException(ResultCodeEnum.UPLOAD_FILE_ERROR);
        }
    }

    @ApiOperation("删除OSS文件")
    @PostMapping("/remove")
    public BaseResponse removeFile(
            @ApiParam(name = "url", value = "要删除的文件路径", required = true)
            @RequestBody String url) {

        this.ossService.removeFile(url);

        return BaseResponse.success().message("删除文件成功");
    }
}
