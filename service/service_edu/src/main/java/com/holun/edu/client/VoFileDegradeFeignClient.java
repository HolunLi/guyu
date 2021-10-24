package com.holun.edu.client;

import com.holun.commonutils.Result;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * 该类中的方法是容错方法，如果服务的消费者在调用接口的过程中，服务的提供者出现宕机，就会回调执行对应的容错方法
 */
@Component
public class VoFileDegradeFeignClient implements VodClient {
    @Override
    public Result deleteVideo(String videoId) {
        return Result.error().message("删除视频出错");
    }

    @Override
    public Result deleteVideos(List<String> videoIdList) {
        return Result.error().message("删除多个视频出错");
    }
}
