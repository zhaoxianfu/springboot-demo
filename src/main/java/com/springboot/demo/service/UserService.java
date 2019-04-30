package com.springboot.demo.service;

import com.springboot.demo.pojo.User;

import java.util.List;

/**
 * @ClassName:UserService
 * @Despriction:
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/17  13:53
 * @Version1.0
 **/

public interface UserService {

    /**
     * 获取用户信息
     *
     * @param tel
     * @return
     */
    String findUserName(String tel);

    List<User> findUsers(String userName, String note);
}
