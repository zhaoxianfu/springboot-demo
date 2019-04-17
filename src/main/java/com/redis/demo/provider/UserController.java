package com.redis.demo.provider;

import com.redis.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName:UserController
 * @Despriction: 测试日志打印的接口
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/17  13:51
 * @Version1.0
 **/

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
    public String findUserNameByTel(@RequestParam("tel") String tel) {
        return userService.findUserName(tel);
    }

}
