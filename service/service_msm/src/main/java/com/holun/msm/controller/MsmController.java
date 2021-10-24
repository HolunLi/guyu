package com.holun.msm.controller;

import com.holun.commonutils.Result;
import com.holun.msm.service.MsmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "使用腾讯云短信服务发送短信")
@RestController
@RequestMapping("/msm")
@CrossOrigin
public class MsmController {
    @Autowired
    private MsmService msmService;

    //发送短信的方法
    @ApiOperation("发送短信")
    @GetMapping("send/{phone}")
    public Result sendMsm(@PathVariable String phone) {
        Result result = msmService.sendMessage(phone);

        return result;
    }
}
