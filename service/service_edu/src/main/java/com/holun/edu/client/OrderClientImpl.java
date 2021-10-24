package com.holun.edu.client;

import com.holun.servicebase.exceptionhandler.GuliException;
import org.springframework.stereotype.Component;

@Component
public class OrderClientImpl implements OrderClient {
    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        throw new GuliException(20001, "出现熔断，调用失败");
    }
}
