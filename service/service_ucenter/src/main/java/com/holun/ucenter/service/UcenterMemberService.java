package com.holun.ucenter.service;

import com.holun.commonutils.Result;
import com.holun.ucenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.holun.ucenter.entity.vo.LoginVo;
import com.holun.ucenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author holun
 * @since 2021-10-10
 */
public interface UcenterMemberService extends IService<UcenterMember> {
    //用户登录
    Result login(LoginVo member);

    //用户注册
    Result register(RegisterVo registerVo);

    //根据openid判断，查询用户
    UcenterMember getOpenIdMember(String openid);

    //查询某一天注册的人数
    Integer countRegisterDay(String day);
}
