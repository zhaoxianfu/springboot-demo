package com.redis.demo.redisdemo.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName:User
 * @Despriction: //TODO
 * @Author:zhaoxianfu
 * @Date:Created 2019/3/27  17:57
 * @Version1.0
 **/
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 6965678214030390632L;

    public static final String Table = "t_user";

    private String name;
    private String address;
    private Integer age;
}
