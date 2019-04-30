package com.springboot.demo;

import com.springboot.demo.task.TestTask;
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
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootDemoApplication.class)
public class AsyncTaskTest {

    @Autowired
    private TestTask asyncTask;

    @Test
    public void testAsyncTask() throws Exception {

        //执行异步任务的开始时间
        long starttime = System.currentTimeMillis();

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
        System.out.println("执行两个异步任务总用时：" + (endtime - starttime) + "毫秒");
    }

}
