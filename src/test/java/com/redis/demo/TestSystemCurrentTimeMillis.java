package com.redis.demo;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName:TestSystemCurrentTimeMillis
 * @Despriction: 测试三种获取毫秒值的方是获取针对什么时间获取的毫秒值

 * @Author:zhaoxianfu
 * @Date:Created 2019/4/11  15:02
 * @Version1.0
 **/
public class TestSystemCurrentTimeMillis {

    public static void main(String[] args) {

// 这三种方式都是获取根据当前系统时间距离GMT下的东8区的时间毫秒值---也就是在1970-01-01 08:00:00基础上
        //只需要记住时间戳就是时间毫秒的差值即可
        long l = System.currentTimeMillis();
        System.out.println(l);             //1555131277280

        Date date = new Date();
        long l1 = date.getTime();
        System.out.println(l1);            //1555131277280

        Calendar instance = Calendar.getInstance();
        long timeInMillis = instance.getTimeInMillis();
        System.out.println(timeInMillis);  //1555131277280

        //根据日历获取date，然后再获取毫秒值
        long time = instance.getTime().getTime();
        System.out.println(time);         //1555131277280

        //获取东8区时区下的时间
        Date date1 = new Date(1555131277280L);
        System.out.println(date1);       //Sat Apr 13 12:54:37 CST 2019

        //获取的是零时区的时间和时间戳的秒数
        Instant instant = Instant.now();
        System.out.println(instant);    //2019-04-13T03:22:47.897Z
        long epochSecond = instant.getEpochSecond();
        System.out.println(epochSecond);  //1555131277   这样获取秒值和通过上面方式获取的时间戳的毫秒值是一致的,都是时间差

    }
}
