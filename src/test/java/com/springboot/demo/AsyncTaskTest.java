package com.springboot.demo;

import com.springboot.demo.task.AsyncTask;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Future;

/**
 * @ClassName:AsyncTaskTest
 * @Despriction:
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/1  11:21
 * @Version1.0
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootDemoApplication.class)
public class AsyncTaskTest {

    @Autowired
    private AsyncTask asyncTask;

    @Test
    public void testAsyncTask() throws Exception {

        //执行异步任务的开始时间
        long starttime = System.currentTimeMillis();

        log.info("当前的主线程的线程名称为{}",Thread.currentThread().getName());

        Future<String> task1 = asyncTask.test1();
        Future<String> task2 = asyncTask.test2();
        //等待任务都执行完
        while (true) {
            if (task1.isDone() && task2.isDone()) {
                break;
            }
        }
        //执行异步任务的结束时间
        long endtime = System.currentTimeMillis();
        log.info("执行两个异步任务总用时：" + (endtime - starttime) + "毫秒");
    }

}
