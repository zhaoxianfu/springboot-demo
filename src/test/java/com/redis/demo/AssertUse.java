package com.redis.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

/**
 * @ClassName:TestNotNull
 * @Despriction:
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/27  1:42
 * @Version1.0
 **/

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisDemoApplication.class)
public class AssertUse {

    @Test
    public void assertTest() {
        //当前面为false的时候,就会抛出异常,进行打印这个日志信息,我们也可以在上层try catch这个异常,从而不终止程序
        Assert.state(false, "好好努力,加油");  //java.lang.IllegalStateException: 好好努力,加油
    }
}
