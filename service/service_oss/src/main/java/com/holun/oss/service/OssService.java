package com.holun.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    //讲师头像上传到阿里云oss
    String uploadFileToAliyunOss(MultipartFile file);
}
