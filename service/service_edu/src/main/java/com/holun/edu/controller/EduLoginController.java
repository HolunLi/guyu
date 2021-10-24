package com.holun.edu.controller;

import com.holun.commonutils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户管理")
@RestController
@RequestMapping("/edu/user")
@CrossOrigin  //解决跨域
public class EduLoginController {

    @ApiOperation("用户登录")
    @PostMapping("login")
    public Result login() {
        return Result.ok().data("token","admin");
    }

    @ApiOperation("获取用户信息")
    @GetMapping("info")
    public Result info() {
        return Result.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
