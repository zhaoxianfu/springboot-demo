package com.springboot.demo.pojo;

import lombok.Data;

/**
 * @ClassName:Student
 * @Despriction:
 * @Author:zhaoxianfu
 * @Date:Created 2019/3/31  20:36
 * @Version1.0
 **/
@Data
public class Student {
    private String name;

    private Integer age;

    private String sex;

    private Teacher teacher;
}
