package com.holun.ucenter.controller;

import com.holun.commonutils.JwtUtils;
import com.holun.commonutils.Result;
import com.holun.servicebase.entity.MemberInfo;
import com.holun.ucenter.entity.UcenterMember;
import com.holun.ucenter.entity.vo.LoginVo;
import com.holun.ucenter.entity.vo.RegisterVo;
import com.holun.ucenter.service.UcenterMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author holun
 * @since 2021-10-10
 */
@Api(tags = "用户中心")
@RestController
@RequestMapping("/ucenter")
@CrossOrigin
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService ucenterMemberService;

    @ApiOperation("用户登录")
    @PostMapping("login")
    public Result loginUser(@RequestBody LoginVo member){
        Result result = ucenterMemberService.login(member);

        return result;
    }

    @ApiOperation("用户注册")
    @PostMapping("register")
    public Result registerUser(@RequestBody RegisterVo registerVo){
        Result result = ucenterMemberService.register(registerVo);

        return result;
    }

    @ApiOperation("根据token获取用户信息")
    @GetMapping("getMemberInfo")
    public Result getMemberInfo(HttpServletRequest request) {
        //调用jwt工具类中的方法，从request对象的头信息中获取token，再从cookie中获取用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //根据用户id获取用户信息
        UcenterMember member = ucenterMemberService.getById(memberId);

        return Result.ok().data("userInfo", member);
    }

    @ApiOperation("更据用户id，查询用户信息（用户昵称和用户头像等）")
    @GetMapping("getMemberInfo/{memberId}")
    public MemberInfo getMemberInfo(@PathVariable(name = "memberId") String memberId) {
        UcenterMember member = ucenterMemberService.getById(memberId);
        MemberInfo memberInfo = new MemberInfo();
        BeanUtils.copyProperties(member, memberInfo);

        return memberInfo;
    }

    @ApiOperation("查询某一天注册的人数")
    @GetMapping("countRegister/{day}")
    public Result countRegister(@PathVariable(name = "day") String day){
        Integer count = ucenterMemberService.countRegisterDay(day);

        return Result.ok().data("countRegister", count);
    }
}

