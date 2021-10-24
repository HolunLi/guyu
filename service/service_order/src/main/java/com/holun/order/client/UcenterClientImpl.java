package com.holun.order.client;

import com.holun.servicebase.entity.MemberInfo;
import com.holun.servicebase.exceptionhandler.GuliException;
import org.springframework.stereotype.Component;

@Component
public class UcenterClientImpl implements UcenterClient {
    @Override
    public MemberInfo getMemberInfo(String memberId) {
        throw new GuliException(20001, "出现熔断,获取用户信息失败");
    }
}
