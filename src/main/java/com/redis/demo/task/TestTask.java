package com.redis.demo.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * @ClassName:AsyncDemo
 * @Despriction: //所有的异步任务必须放在一个专门的类中,调用异步任务的方法和异步任务方法不能放在一个类中
 *               异步任务类
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/1  11:13
 * @Version1.0
 **/

@Slf4j
@Component
public class TestTask {

    //异步任务此处必须返回Future，可以是Future<String>，也可以是Future<Integer>，自己定义具体的返回内容
    @Async
    public Future<String> test1() throws Exception{

        //这个线程睡1秒,也就是这个异步任务方法执行用时1秒
        Thread.sleep(1000);
        System.out.println("执行任务1，用时1秒");
        return new AsyncResult<>("test1");
    }

    @Async
    public Future<String> test2() throws Exception{

        //这个线程睡1.5秒,也就是这个异步任务方法执行用时1.5秒
        Thread.sleep(1500);
        System.out.println("执行任务2，用时1.5秒");
        return new AsyncResult<>("test2");
    }
}
