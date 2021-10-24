package com.holun.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient //开启服务发现功能，在nacos中注册服务
@EnableFeignClients //通过Feign客户端,调用在nacos中注册的服务（服务的消费者）
@ComponentScan(basePackages = {"com.holun"})
public class EduApplication {
    public static void main(String[] args) {
        //关闭nacos默认的logback配置，不关闭会与本模块中配置的logback-spring起冲突
        System.setProperty("nacos.logging.default.config.enabled", "false");
        SpringApplication.run(EduApplication.class, args);
    }
}
