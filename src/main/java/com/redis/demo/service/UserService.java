package com.redis.demo.service;

/**
 * @ClassName:UserService
 * @Despriction: //TODO
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
}
