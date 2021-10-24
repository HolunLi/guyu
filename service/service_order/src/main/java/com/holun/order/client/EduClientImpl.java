package com.holun.order.client;

import com.holun.servicebase.entity.CourseInfo;
import com.holun.servicebase.exceptionhandler.GuliException;
import org.springframework.stereotype.Component;

@Component
public class EduClientImpl implements EduClient {
    @Override
    public CourseInfo getCourseInfoOrder(String courseId) {
        throw new GuliException(20001, "出现熔断,获取课程信息失败");
    }
}
