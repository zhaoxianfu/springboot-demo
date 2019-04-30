package com.springboot.demo.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @ClassName:ScheduledTask
 * @Despriction:
 * 定时任务类---是一个串行的方式去执行这个定时任务---单线程
 * 如果定时任务特别多的话，就需要创建一个线程池去执行大量的定时任务
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/1  11:32
 * @Version1.0
 **/

@Slf4j
@Component
public class ScheduledTask {

//    @Scheduled(fixedRate = 5000) ：上一次开始执行时间点之后5秒再执行,会在启动的时候执行一次,之后每5秒执行一次
//    @Scheduled(fixedDelay = 5000) ：上一次执行完毕时间点之后5秒再执行
//    @Scheduled(initialDelay=1000, fixedRate=5000) ：第一次延迟1秒后执行，之后按fixedRate的规则每5秒执行一次
//    @Scheduled(cron="*/5 * * * * *") ：通过cron表达式定义规则
//    注意，这里的时间，单位是毫秒，1秒=1000毫秒

   /* *//**
     * 定时任务1
     * 每间隔10秒输出时间
     *//*
    @Scheduled(fixedRate = 10000)
    public void logTime1() {
        log.info("定时任务1,现在时间：" + System.currentTimeMillis());
    }

    *//**
     * 定时任务2---为了测试多线程下的多个任务调用类执行多个定时任务
     *//*
    @Scheduled(fixedRate = 5000)
    public void logTime2() {
        log.info("定时任务2,现在时间：" + System.currentTimeMillis());
    }*/
}
