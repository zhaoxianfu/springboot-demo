package com.redis.demo.config;

import feign.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName:FeignConfig
 * @Despriction:
 * 通过logging.level.xx=debug来设置日志级别。然而这个对Fegin客户端而言不会产生效果。
 * 因为@FeignClient注解修改的客户端在被代理时，
 * 都会创建一个新的Fegin.Logger实例。我们需要额外指定这个日志的级别才可以
 * @Author:zhaoxianfu
 * @Date:Created 2018/9/17  10:01
 * @Version1.0
 **/

@Slf4j
@Configuration
public class FeignConfig {

    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }
}
