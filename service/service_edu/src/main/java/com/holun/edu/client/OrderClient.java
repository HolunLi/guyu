package com.holun.edu.client;

import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-order", fallback = OrderClientImpl.class)
public interface OrderClient {
    @GetMapping("/order/front/isBuyCourse/{courseId}/{memberId}")
    boolean isBuyCourse(@ApiParam("课程id") @PathVariable(name = "courseId") String courseId, @ApiParam("用户id") @PathVariable(name = "memberId") String memberId);
}
