package com.holun.ucenter.controller;

import com.google.gson.Gson;
import com.holun.commonutils.JwtUtils;
import com.holun.servicebase.exceptionhandler.GuliException;
import com.holun.ucenter.entity.UcenterMember;
import com.holun.ucenter.service.UcenterMemberService;
import com.holun.ucenter.utils.ConstantWxUtils;
import com.holun.ucenter.utils.HttpClientUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.net.URLEncoder;
import java.util.HashMap;

@Api(tags = "接入微信登录")
@Controller  //只是请求地址，不需要返回数据
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
public class WxApiController {
    @Autowired
    private UcenterMemberService memberService;

    /**
     * 用户扫码后，就会回调该方法
     */
    @ApiOperation("获取扫描人信息，添加数据")
    @GetMapping("callback")
    public String callback(String code, String state) { //code值，临时票据，类似于验证码
        try {
            //第一步：拿着code，请求微信固定的地址，获取accsess_token和openid
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            //拼接三个参数 ：id,秘钥和code值
            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET,
                    code
            );
            //使用httpclient发送请求，得到返回结果（httpclient可以模拟浏览器发送请求）
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            System.out.println(accessTokenInfo);
            //使用Gson，把accessTokenInfo字符串转换map集合，根据map里面key获取对应值
            Gson gson = new Gson();
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            //从accessTokenInfo字符串获取出来两个值 accsess_token 和 openid
            String access_token = (String)mapAccessToken.get("access_token");
            String openid = (String)mapAccessToken.get("openid");

            UcenterMember member = memberService.getOpenIdMember(openid);
            //判断数据表里面是否存在相同微信信息
            if(member == null) {//memeber是空，表示没有相同微信数据，进行添加
                //第二步：拿着得到accsess_token 和 openid，再去请求微信提供固定的地址，获取到扫描人信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                //拼接两个参数，accsess_token 和 openid
                String userInfoUrl = String.format(
                        baseUserInfoUrl,
                        access_token,
                        openid
                );
                //使用httpclient发送请求，得到返回结果
                String userInfo = HttpClientUtils.get(userInfoUrl);
                System.out.println("userInfo" + userInfo);
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                //userinfo字符串中获取扫描人信息
                String nickname = (String)userInfoMap.get("nickname");//昵称
                String headimgurl = (String)userInfoMap.get("headimgurl");//头像

                member = new UcenterMember();
                member.setOpenid(openid);
                member.setNickname(nickname);
                member.setAvatar(headimgurl);
                //把扫描人信息添加数据库里面
                memberService.save(member);
            }

            //使用jwt根据用户id和用户昵称，生成token字符串
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            //返回首页面，通过路径传递token字符串
            return "redirect:http://localhost:3000?token="+jwtToken;
        }catch(Exception e) {
            throw new GuliException(20001, "登录失败");
        }
    }

    //生成微信扫描二维码
    @GetMapping("login")
    public String getWxCode() {
        try {
            //固定地址，后面拼接参数
            //微信开放平台授权的baseUrl  %s相当于?代表占位符，生成微信二维码，只需要往这个baseUrl后面拼接一些参数就行
            String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                    "?appid=%s" +
                    "&redirect_uri=%s" +
                    "&response_type=code" +
                    "&scope=snsapi_login" +
                    "&state=%s" +
                    "#wechat_redirect";

            //对redirect_url进行URLEncoder编码
            String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");

            //设置%s里面值
            String url = String.format(
                    baseUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    redirectUrl,
                    "atguigu"
            );

            //重定向到请求微信地址里面
            return "redirect:"+url;
        }catch(Exception e) {
            throw new GuliException(20001, "生成验证码失败");
        }
    }
}
