package com.springboot.demo;

import com.springboot.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName:MockTest
 * @Despriction: mock测试
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/23  18:17
 * @Version1.0
 **/

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootDemoApplication.class)
public class MockTest {

    //@MockBean表示对哪个bean进行mock测试
    @MockBean
    private UserService userService;

    /**
     * mock测试1
     */
    @Test
    public void mockTest1() {
        //构建mock时返回虚拟对象
        String str = "赵宪福";

        //指定mock bean的方法和参数
        BDDMockito.given(this.userService.findUserName("1"))
                //指定返回的虚拟对象
                .willReturn(str);

        String userName = this.userService.findUserName("1");
        log.info(userName);
        System.err.println(userName);
    }
}
