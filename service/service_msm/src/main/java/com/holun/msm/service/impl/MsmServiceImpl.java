package com.holun.msm.service.impl;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.holun.commonutils.Result;
import com.holun.msm.entity.SMSParameter;
import com.holun.msm.service.MsmService;
import com.holun.msm.utills.VerifyCode;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class MsmServiceImpl implements MsmService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Result sendMessage(String phone) {
        SMSParameter smsParameter = new SMSParameter();

        try {
            //从redis获取验证码，如果获取到直接返回
            String code = redisTemplate.opsForValue().get(phone);
            if(!StringUtils.isEmpty(code))
                return Result.ok();

            //redis中不存在生成验证码
            code = VerifyCode.createRandom(true,4);
            String[] params = {code, "1"}; //验证码和验证码的生效时间
            SmsSingleSender sender = new SmsSingleSender(smsParameter.getAppId(), smsParameter.getAppKey());
            SmsSingleSenderResult result = sender.sendWithParam("86", phone, smsParameter.getTemplateId(), params, smsParameter.getSmsSign(), "", "");
            //发送短信成功，就将code存放的缓存中
            if (result.result == 0) {
                //设置验证码有效时间为1分钟
                redisTemplate.opsForValue().set(phone, code,1, TimeUnit.MINUTES);
                return Result.ok();
            }
            return Result.error().message("发送短信失败");
        } catch (HTTPException e) {
            // HTTP 响应码错误
            e.printStackTrace();
            return Result.error().message("发送短信失败");
        } catch (JSONException e) {
            // JSON 解析错误
            e.printStackTrace();
            return Result.error().message("发送短信失败");
        } catch (IOException e) {
            // 网络 IO 错误
            e.printStackTrace();
            return Result.error().message("发送短信失败");
        }
    }
}
