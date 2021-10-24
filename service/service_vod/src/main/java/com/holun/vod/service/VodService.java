package com.holun.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    //上传视频到阿里云VoD
    String uploadVideoToAliyunVoD(MultipartFile file);

    //根据视频id,删除阿里云VoD中存储的视频
    void deleteVideoById(String videoId);

    //根据多个视频id,删除阿里云VoD中存储的多个视频（批量删除）
    void deleteVideosByVideoIdList(List<String> videoIdList);
}
