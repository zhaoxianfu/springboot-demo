package com.springboot.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.*;

/**
 * @ClassName:ScheduledConfig
 * @Despriction: 创建定时任务线程池并行执行----可以使很多的定时任务去同时执行
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/1  11:42
 * @Version1.0
 **/

@Slf4j
@Configuration
public class ScheduledConfig implements SchedulingConfigurer {

    /**
     * 把创建的线程池对象注册到定时任务里面去,这样定时任务执行对象就会有多个线程去执行多个定时任务了
     *
     * @param scheduledTaskRegistrar
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(setTaskExecutors());
    }

    /**
     * 创建一个定时任务的线程池对象
     *
     * @return
     */
    @Bean
    public Executor setTaskExecutors() {
        // 创建4个线程的线程池来处理
        return Executors.newScheduledThreadPool(4);

    }
}
