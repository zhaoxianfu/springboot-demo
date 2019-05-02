package com.springboot.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @ClassName:AsyncConfig
 * @Despriction: 异步任务线程池--->从这里取线程去执行异步任务
 * @Author:zhaoxianfu
 * @Date:Created 2019/5/2  23:34
 * @Version1.0
 **/

@Slf4j
@Configuration
public class AsyncConfig implements AsyncConfigurer {

    /**
     * 定义异步任务的线程池
     *
     * @return
     */
    @Override
    public Executor getAsyncExecutor() {
        //定义线程池
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        //核心线程数
        threadPoolTaskExecutor.setCorePoolSize(10);
        //线程池最大线程数
        threadPoolTaskExecutor.setMaxPoolSize(30);
        //线程队列最大线程数
        threadPoolTaskExecutor.setQueueCapacity(2000);
        //初始化
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
}
