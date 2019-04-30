package com.springboot.demo;

import com.alibaba.fastjson.JSONObject;
import com.springboot.demo.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootDemoApplication.class)
public class RestTemplateDemoTest {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 通过RestTemplate的getForObject()方法，
     * 传递url地址及实体类的字节码，RestTemplate会自动发起请求，接收响应，并且帮我们对响应结果进行反序列化。
     */
    @Test
    public void test1() {
        //getForObject和getForEntity区别是第一个直接获取的是返回值对象,第二个是可以获取返回值对象的状态码和HttpHeaders等,通过getbody可以获取返回值对象
        User user = this.restTemplate.getForObject("http://localhost/hello", User.class);
        this.restTemplate.getForObject("http://localhost/hello", User.class);
        ResponseEntity<User> entity = this.restTemplate.getForEntity("http://localhost/hello", User.class);
        System.out.println(user);
        JSONObject parm = new JSONObject();
    }

    @Test
    public void test2() {
        //该方法通过restTemplate请求远程restfulAPI
        HttpHeaders headers = new HttpHeaders();
        headers.set("auth_token", "asdfgh");
        headers.set("Other-Header", "othervalue");
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject parm = new JSONObject();
        parm.put("parm", "1234");

        String jsonString = parm.toJSONString();
        System.out.println(jsonString);
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        System.out.println(jsonObject.toJSONString());

        HttpEntity<JSONObject> entity = new HttpEntity<>(parm, headers); //这里放JSONObject--json对象或者json格式的string类型都可以,根据泛型去变

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "http://localhost:8080/headerApi", HttpMethod.POST, entity, String.class);

        String body = responseEntity.getBody();
        System.out.println(body);

        JSONObject parseObject = JSONObject.parseObject(body);
        //这样就和给前端json字符串就一样的道理
        //这里放JSONObject, String 都可以。因为JSONObject返回的时候其实也就是string,只不过把json类型的字符串转为了json对象,相当于map格式,可以通过api获取属性值

        //JSONObject对象里面放JSONObject,一层嵌套一层
    }

}
