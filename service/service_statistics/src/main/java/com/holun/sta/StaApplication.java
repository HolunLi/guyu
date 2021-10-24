package com.holun.sta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableDiscoveryClient //开启服务发现。主程序类在启动时，就会将该服务在注册中心中注册
@EnableFeignClients //开启feign客户端。通过feign客户端调用某个服务中提供的接口
@SpringBootApplication
@ComponentScan(basePackages = {"com.holun"})
@EnableScheduling //开启定时任务
public class StaApplication {
    public static void main(String[] args) {
        SpringApplication.run(StaApplication.class, args);
    }
}
