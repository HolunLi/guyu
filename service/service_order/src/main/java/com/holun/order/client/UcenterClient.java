package com.holun.order.client;

import com.holun.servicebase.entity.MemberInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-ucenter", fallback = UcenterClientImpl.class) //fallback是回调，rollback是回滚
@Component
public interface UcenterClient {
    @GetMapping("/ucenter/getMemberInfo/{memberId}")
    MemberInfo getMemberInfo(@PathVariable(name = "memberId") String memberId);
}
