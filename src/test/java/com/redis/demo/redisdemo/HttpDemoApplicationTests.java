package com.redis.demo.redisdemo;

import com.redis.demo.redisdemo.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisDemoApplication.class)
public class HttpDemoApplicationTests {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 通过RestTemplate的getForObject()方法，
     * 传递url地址及实体类的字节码，RestTemplate会自动发起请求，接收响应，并且帮我们对响应结果进行反序列化。
     */
    @Test
    public void httpGet() {
        User user = this.restTemplate.getForObject("http://localhost/hello", User.class);
        System.out.println(user);
    }

}
