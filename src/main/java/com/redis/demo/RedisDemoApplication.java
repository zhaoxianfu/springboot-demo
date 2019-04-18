package com.redis.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 服务的启动类
 * @author zhaoxianfu
 */

@SpringBootApplication(scanBasePackages = {"com.redis.demo"})     //可以加载多个模块下的所有的bean
@EnableSwagger2                                      //开启swagger文档
@EnableCaching                                      //开启spring的缓存
@EnableTransactionManagement                       //开启声明式事务
@MapperScan("com.redis.demo.mapper")                 //配置扫描mapper接口，然后创建对象放到IOC容器中,使用这个注解包扫描就不用在每个DO对象上加@Mapper注解了
@EnableAsync                                     //开启异步任务
@EnableScheduling                               //开启定时任务
@EnableFeignClients("com.redis.demo")          //可以加载到本项目中所有的子模块中的@FeignClient注解的bean,basePackages属性会加载依赖里面的
public class RedisDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisDemoApplication.class, args);
        System.out.println("<<<<<<<<<<<<<<<<<<<<<Application启动成功>>>>>>>>>>>>>>>>>>>>>>");
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
        // 默认的RestTemplate，底层是走JDK的URLConnection方式。这里配置httpClient连接,还有一种okHttpClient连接
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(30 * 1000);
        httpRequestFactory.setConnectTimeout(30 * 3000);
        httpRequestFactory.setReadTimeout(30 * 3000);

        return new RestTemplate(httpRequestFactory);
    }
}
