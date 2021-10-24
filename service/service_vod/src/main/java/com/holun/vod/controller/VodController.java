package com.holun.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.holun.commonutils.Result;
import com.holun.servicebase.exceptionhandler.GuliException;
import com.holun.vod.service.VodService;
import com.holun.vod.utils.ConstantPropertiesUtils;
import com.holun.vod.utils.InitVodClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Api(tags = "管理上传到阿里云VoD上的课程视频")
@RestController
@RequestMapping("/vod")
@CrossOrigin
public class VodController {
    @Autowired
    private VodService vodService;

    @ApiOperation("上传视频到阿里云VoD")
    @PostMapping("uploadVideo")
    public Result uploadVideo(MultipartFile file) {
        String videoId = vodService.uploadVideoToAliyunVoD(file);

        return Result.ok().data("videoId", videoId);
    }

    @ApiOperation("根据视频id,删除阿里云VoD中存储的视频")
    @DeleteMapping("deleteVideo/{videoId}")
    public Result deleteVideo(@ApiParam("视频id") @PathVariable("videoId") String videoId) {
        vodService.deleteVideoById(videoId);

        return Result.ok();
    }

    @ApiOperation("根据多个视频id,删除阿里云VoD中存储的多个视频（批量删除）")
    @DeleteMapping("deleteVideos")
    public Result deleteVideos(@RequestParam("videoIdList") List<String> videoIdList) {
        vodService.deleteVideosByVideoIdList(videoIdList);

        return Result.ok();
    }

    @ApiOperation("根据视频id获取视频凭证")
    @GetMapping("getPlayAuth/{id}")
    public Result getPlayAuth(@PathVariable String id){
        try{
            //创建初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantPropertiesUtils.ACCESS_KEY_ID, ConstantPropertiesUtils.ACCESS_KEY_SECRET);
            //创建用于获取视频凭证的request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //向request设置视频id
            request.setVideoId(id);
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            //调用方法得到凭证
            String playAuth = response.getPlayAuth();

            return Result.ok().data("playAuth", playAuth);
        }catch (Exception e){
            throw new GuliException(20001, "获取凭证失败");
        }
    }
}
