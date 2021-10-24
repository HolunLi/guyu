package com.holun.sta.client;

import com.holun.commonutils.Result;
import com.holun.servicebase.exceptionhandler.GuliException;
import org.springframework.stereotype.Component;

@Component
public class UcenterClientImpl implements UcenterClient {
    @Override
    public Result countRegister(String day) {
        throw new GuliException(20001, "出现熔断，调用失败");
    }
}
