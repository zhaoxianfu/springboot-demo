package com.redis.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * @ClassName:WwwHealthindicator
 * @Despriction: 在health健康端点里面进行添加健康项, 当访问health端点时可以展示这个健康项的情况, 这里以访问百度成不成功为例
 * #actuator的health端点默认提供一些常用的指标项---关于数据库和rabbitMq,MongoDB,Redis等
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/24  15:07
 * @Version1.0
 **/

@Slf4j
@Component
public class WwwHealthindicator extends AbstractHealthIndicator {

    /**
     * string类型设置为final不能够变化,因为指向不同的string字符串了
     * 如果为其他对象类型的话,指的是堆中的地址,对象的内容是可以变的
     */
    private final static String BAIDU_HOST = "www.baidu.com";

    /**
     * 超时时间
     */
    private final static int TIME_OUT = 3000;

    /**
     * 监测服务器能不能访问到百度
     *
     * @param builder
     * @throws Exception
     */
    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        //通过访问百度服务器,看是否能够访问互联网
        boolean status = ping();
        if (status) {
            //健康指标为可用状态,并添加一个消息项
            builder.up();
            builder.withDetail("message", "当前服务器可以访问百度互联网");
        } else {
            //健康指标为不可用状态
            builder.down();
            builder.withDetail("message", "当前服务器无法访问百度互联网");
        }
    }

    /**
     * 当返回值为true时,说明host是可用的,为false则为不可用
     *
     * @return
     * @throws Exception
     */
    private boolean ping() throws Exception {
        try {
            return InetAddress.getByName(BAIDU_HOST).isReachable(TIME_OUT);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
