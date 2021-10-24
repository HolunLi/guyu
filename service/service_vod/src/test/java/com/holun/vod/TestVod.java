package com.holun.vod;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import java.util.List;

public class TestVod {

    public static void main(String[] args) throws Exception{
        getPlayAuth();
        //getPlayUrl();
    }

    //根据视频id获取视频播放凭证（上传到阿里云视频点播服务中的视频，会默认被分配一个唯一的id）
    public static void getPlayAuth() throws Exception{
        //创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("xxx", "xxx");
        //创建用于获取视频播放凭证的request和response对象
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        //向request对象里面设置视频id
        request.setVideoId("55c4c0cab561452a8b5bd393d4a834be");
        response = client.getAcsResponse(request);
        //获取视频播放凭证
        String playAuth = response.getPlayAuth();
        System.out.println("playAuth:"+playAuth);
    }

    //根据视频Id获取视频播放地址
    public static void getPlayUrl() throws Exception{
        //创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("xxx", "xxx");

        //创建获取视频地址的request和response对象
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        //向request对象里面设置视频id
        request.setVideoId("55c4c0cab561452a8b5bd393d4a834be");
        //调用初始化对象里面的方法，传递request，获取数据
        response = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //获取播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }
}
