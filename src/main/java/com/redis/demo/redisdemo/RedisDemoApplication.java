package com.redis.demo.redisdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableTransactionManagement
public class RedisDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisDemoApplication.class, args);
        System.out.println("<<<<<<<<<<<<<<<<<<RedisDemoApplication启动成功>>>>>>>>>>>>>>>>>>>>>>");
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.redis.demo.redisdemo"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("RedisDemo接口文档")
                .description("API RedisDemo接口文档")
                .termsOfServiceUrl(" API terms of service")
                .version("1.0.0")
                .build();
    }

    /**
     * 首先在项目中注册一个RestTemplate对象,可以在启动类位置注册：
     * 实现了对象与json的序列化和反序列化
     *
     * @return
     */
    @Bean
    public RestTemplate restTemplate() {
        // 默认的RestTemplate，底层是走JDK的URLConnection方式。
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    }
}
