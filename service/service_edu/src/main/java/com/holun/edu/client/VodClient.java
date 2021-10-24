package com.holun.edu.client;

import com.holun.commonutils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@FeignClient(name = "service-vod", fallback = VoFileDegradeFeignClient.class) //fallback是回调，rollback是回滚
@Component
public interface VodClient {
    @DeleteMapping("/vod/deleteVideo/{videoId}")
    Result deleteVideo(@PathVariable("videoId") String videoId);

    @DeleteMapping("/vod/deleteVideos")
    Result deleteVideos(@RequestParam("videoIdList") List<String> videoIdList);
}
