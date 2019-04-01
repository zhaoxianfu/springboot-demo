package com.redis.demo.redisdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis.demo.redisdemo.pojo.Student;
import com.redis.demo.redisdemo.pojo.Teacher;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @ClassName:TestHttpDemo
 * @Despriction: //TODO
 * @Author:zhaoxianfu
 * @Date:Created 2019/3/31  20:32
 * @Version1.0
 **/
@Slf4j
public class TestHttpDemo {
    public static void main(String[] args) throws IOException {
        // json处理工具ObjectMapper
        ObjectMapper mapper = new ObjectMapper();

        Teacher teacher = new Teacher();
        teacher.setAge(32);
        teacher.setName("马老师");
        teacher.setSex("男");

        Student student = new Student();
        student.setAge(11);
        student.setName("赵宪福");
        student.setSex("男");
        student.setTeacher(teacher);

        // 序列化,得到对象集合的json字符串
        String json = mapper.writeValueAsString(student);
        System.out.println(json);

        // 反序列化，接收两个参数：json数据，反序列化的目标类字节码
        Student student1 = mapper.readValue(json, Student.class);
        System.out.println(student1);
    }
}
