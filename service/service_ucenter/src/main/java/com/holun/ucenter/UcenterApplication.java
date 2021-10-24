package com.holun.ucenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 用户中心（登录和注册）
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.holun"})
@EnableDiscoveryClient //在nacos中注册服务
public class UcenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class, args);
    }
}
