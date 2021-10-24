package com.holun.vod;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 将课程视频上传到阿里云视频点播服务（VoD）
 * vod微服务模块，不进行数据库操作，所以在启动时，要排除数据源的自动配置
 * 因为该项目中，我的是Druid数据源，所以还需要排除DruidDataSource的自动配置
 *
 * 拓展：
 * 视频点播（ApsaraVideo VoD，简称VoD）
 * 是集视频采集、编辑、上传、媒体资源管理、自动化转码处理（窄带高清™）、视频审核分析、分发加速于一体的一站式音视频点播解决方案。
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class})
@EnableDiscoveryClient //在nacos中注册服务
@ComponentScan(basePackages = {"com.holun"})
public class VodApplication {
    public static void main(String[] args) {
        SpringApplication.run(VodApplication.class, args);
    }
}
