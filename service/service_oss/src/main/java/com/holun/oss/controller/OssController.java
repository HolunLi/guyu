package com.holun.oss.controller;

import com.holun.commonutils.Result;
import com.holun.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "管理上传到oss上的文件")
@RestController
@RequestMapping("/oss")
@CrossOrigin
public class OssController {
    @Autowired
    private OssService ossService;

    /**
     * 讲师表中的avatar字段，表示讲师头像
     * 因为上传的头像是使用阿里云oss存储，所以该字段存储不是头像本身，而是头像上传到oss的路径
     */
    @ApiOperation("上传文件到阿里云oss")
    @PostMapping("uploadFile")
    public Result uploadFile(MultipartFile file) { //MultipartFile对象用于接收从前端上传的文件
        String url = ossService.uploadFileToAliyunOss(file); //url是文件上传到oss的路径

        return Result.ok().data("url", url);
    }
}
