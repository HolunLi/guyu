package com.holun.sta.client;

import com.holun.commonutils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-ucenter", fallback = UcenterClientImpl.class)
public interface UcenterClient {
    @GetMapping("/ucenter/countRegister/{day}")
    Result countRegister(@PathVariable(name = "day") String day);
}
