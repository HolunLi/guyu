package com.holun.servicebase.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger在线接口文档
 * 在前后端分离的开发模式中，swagger接口文档是前后端开发人员最好的沟通方式
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     *创建接口文档
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2) //DocumentationType.SWAGGER_2
                .apiInfo(apiInfo())
                .enable(true) //设置是否开启swagger,默认为true。如果设置为false，则无法在浏览器中使用swagger
                .groupName("webApi") //分组名
                .select()
                .paths(Predicates.not(PathSelectors.regex("/admin/.*")))
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();
    }

    /**
     *自定义接口文档中的信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("谷粒学院接口文档") //文档标题
                .description("在该文档中描述了各个接口的作用")  //文档描述
                .version("1.0")
                .contact(new Contact("Holun",null,"Holun_Li@163.com")) //联系（编写者的信息）
                .build();  //建立
    }

}


