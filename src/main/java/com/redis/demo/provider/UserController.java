package com.redis.demo.provider;

import com.redis.demo.pojo.User;
import com.redis.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName:UserController
 * @Despriction: 测试日志打印的接口
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/17  13:51
 * @Version1.0
 **/

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 访问路径 http://localhost:8080/user/findUserNameByTel?tel=1234567
     *
     * @param tel 手机号
     * @return userName
     */
    @RequestMapping("findUserNameByTel")
    public ResponseEntity<User> findUserNameByTel(@RequestParam("tel") String tel) {
        String userName = userService.findUserName(tel);
        User user = new User();
        user.setName(userName);
        user.setAge(21);
        user.setAddress("上海市浦东新区");
        user.setUserName(userName);
        return ResponseEntity.ok().body(user);
    }

    /**
     * 通过＠RequestHeader接收请求头参数,进而进行操作,@RequestHeader里面的tel属性赋值给形式参数tel
     *
     * @param tel
     * @return
     */
    @PostMapping("header/user")
    public ResponseEntity<User> headerUser(@RequestHeader("tel") String tel) {
        String userName = userService.findUserName(tel);
        User user = new User();
        user.setName(userName);
        user.setAge(21);
        user.setAddress("上海市浦东新区");
        user.setUserName(userName);
        return ResponseEntity.ok().body(user);
    }

    /**
     * 通过@CookieValue接收cookie中的参数
     *
     * @param name
     * @param age
     * @return
     */
    @RequestMapping("/testCookie")
    public String testCookie(@CookieValue(value = "name", required = false) String name,
                             @CookieValue(value = "age", required = false) Integer age) {
        System.out.println(name + "," + age);
        return "hello";
    }

    /**
     * 通过@SessionAttribute接收服务中session中的参数
     *
     * @param sessionStr
     * @return
     */
    @RequestMapping("/testSessionAttributes")
    public String testSessionAttributes(@SessionAttribute(value = "sessionStr") String sessionStr) {
        System.out.println(sessionStr);
        return "success";
    }

}
