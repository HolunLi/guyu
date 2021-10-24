package com.holun.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.holun.commonutils.JwtUtils;
import com.holun.commonutils.MD5;
import com.holun.commonutils.Result;
import com.holun.servicebase.exceptionhandler.GuliException;
import com.holun.ucenter.entity.UcenterMember;
import com.holun.ucenter.entity.vo.LoginVo;
import com.holun.ucenter.entity.vo.RegisterVo;
import com.holun.ucenter.mapper.UcenterMemberMapper;
import com.holun.ucenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author holun
 * @since 2021-10-10
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Result login(LoginVo member) {
        //获取登录手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();
        //手机号和密码非空判断
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password))
            return Result.error().message("手机号或密码为空");

        //判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);
        //查询对象是否为空
        if(ucenterMember == null)
            return Result.error().message("手机号未注册");

        //判断密码（因为存储到数据库中的密码是加密的，把输入的密码进行加密，再和数据库密码进行比较，加密方式MD5）
        if(!MD5.encrypt(password).equals(ucenterMember.getPassword()))
            return Result.error().message("密码错误");

        //判断用户是否禁用
        if(ucenterMember.getIsDeleted())
            return Result.error().message("用户被禁用，无法登陆");

        //登录成功
        //使用jwt工具,根据用户id和用户昵称，生成token字符串，
        String jwtToken = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
        return Result.ok().data("token", jwtToken);
    }

    @Override
    public Result register(RegisterVo registerVo) {
       //获取注册的数据
        String mobile = registerVo.getMobile(); //手机号
        String nickname = registerVo.getNickname(); //昵称
        String password = registerVo.getPassword(); //密码
        String code = registerVo.getCode(); //验证码

        //非空判断
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(nickname) ||
                StringUtils.isEmpty(password) || StringUtils.isEmpty(code))
            return Result.error().message("手机号、昵称等信息不能为空");

        //判断手机号是否已注册过
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0)
            return Result.error().message("手机号已被使用");

        //获取redis中的验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        //判断验证码是否输入正确
        if(!code.equals(redisCode))
            return Result.error().message("验证码有误");
        //验证码，用完一次就从缓存中移除
        redisTemplate.delete(mobile);

        //数据添加数据库中
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        //用户不禁用
        member.setIsDeleted(false);
        //设置一个默认的用户头像
        member.setAvatar("https://edu-2021-10-01.oss-cn-shanghai.aliyuncs.com/default.jpg");
        baseMapper.insert(member);
        return Result.ok();
    }

    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember member = baseMapper.selectOne(wrapper);

        return member;
    }

    @Override
    public Integer countRegisterDay(String day) {
        Integer count = baseMapper.countRegisterDay(day);

        return count;
    }
}
