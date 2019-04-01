package com.redis.demo.redisdemo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis.demo.redisdemo.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author: HuYi.Zhang
 * @create: 2018-05-06 09:53
 **/
@Slf4j
public class HttpTests {

    private CloseableHttpClient httpClient;

    // json处理工具ObjectMapper
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void init() {
//        创建一个httpClient客户端
        httpClient = HttpClients.createDefault();
    }

    @Test
    public void testGet() throws IOException {
//        发起什么类型的请求
        HttpGet request = new HttpGet("http://www.baidu.com");
//        执行请求，返回响应
        String response = this.httpClient.execute(request, new BasicResponseHandler());
        log.info(response);
    }

    @Test
    public void testPost() throws IOException {
        HttpGet request = new HttpGet("http://www.oschina.net/");
        request.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; W OW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        String response = this.httpClient.execute(request, new BasicResponseHandler());
        log.info(response);
    }

    /**
     * 请求另外一个系统的，调用方式为httpClient
     * 反序列化，将json字符串的格式数据转换为对象
     *
     * @throws IOException
     */
    @Test
    public void testGetPojo() throws IOException {
        HttpGet request = new HttpGet("http://localhost/hello");
        String response = this.httpClient.execute(request, new BasicResponseHandler());
//        System.out.println(response);
        User user = mapper.readValue(response, User.class);
        log.info(user.toString());
    }

    /**
     * json字符串转list集合
     *
     * @throws IOException
     */
    @Test
    public void testJsonList() throws IOException {
        User user = new User();
        user.setId(8L);
        user.setAge(21);
        user.setName("柳岩");
        user.setUserName("liuyan");

        // 序列化,得到对象集合的json字符串
        String json = mapper.writeValueAsString(Arrays.asList(user, user));

        // 反序列化，接收两个参数：json数据，反序列化的目标类字节码
//        Jackson做了一个类型工厂---TypeFactory
        List<User> users = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, User.class));
        for (User u : users) {
            log.info("u = " + u);
        }
    }

    /**
     * 对象泛型关系复杂时，类型工厂也不好使了。这个时候Jackson提供了TypeReference来接收类型泛型，然后底层通过反射来获取泛型上的具体类型。实现数据转换。
     *
     * @throws IOException
     */
    @Test
    public void testJsonTypeReference() throws IOException {
        User user = new User();
        user.setId(8L);
        user.setAge(21);
        user.setName("柳岩");
        user.setUserName("liuyan");

        // 序列化,得到对象集合的json字符串
        String json = mapper.writeValueAsString(Arrays.asList(user, user));

        log.info(json);

        // 反序列化，接收两个参数：json数据，反序列化的目标类字节码
        List<User> users = mapper.readValue(json, new TypeReference<List<User>>() {
        });
        for (User u : users) {
            log.info("u = " + u);
        }
    }
}
