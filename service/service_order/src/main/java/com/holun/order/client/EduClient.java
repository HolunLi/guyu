package com.holun.order.client;

import com.holun.servicebase.entity.CourseInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-edu", fallback = EduClientImpl.class) //fallback是回调，rollback是回滚
@Component
public interface EduClient {
    @GetMapping("/edu/coursefront/getCourseInfoOrder/{courseId}")
    CourseInfo getCourseInfoOrder(@PathVariable(name = "courseId") String courseId);
}
