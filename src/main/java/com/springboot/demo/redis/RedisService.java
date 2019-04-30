package com.springboot.demo.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName:RedisService
 * @Despriction: redis中key和value都为string类型时的操作的工具类----直接通过模板类对象操作key相关的过期时间等
 * @Author:zhaoxianfu
 * @Date:Created 2019/3/27  17:12
 * @Version1.0
 **/

@Slf4j
@Component
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 设置的默认过期时长为一天，单位：秒
     */
    public static final long DEFAULT_EXPIRE = 60 * 60 * 24;

    /**
     * 不设置过期时长,为负数时为不设置过期时长
     */
    public static final long NOT_EXPIRE = -1;

    /**
     * 判断是否存在该key下的数据
     *
     * @param key
     * @return
     */
    public boolean existsKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 重名名key，如果newKey已经存在，则newKey的原值被覆盖
     *
     * @param oldKey
     * @param newKey
     */
    public void renameKey(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    /**
     * newKey不存在时才重命名
     *
     * @param oldKey
     * @param newKey
     * @return 修改成功返回true
     */
    public boolean renameKeyNotExist(String oldKey, String newKey) {
        return redisTemplate.renameIfAbsent(oldKey, newKey);
    }

    /**
     * 删除key
     *
     * @param key
     */
    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 删除多个key
     *
     * @param keys
     */
    public void deleteKey(String... keys) {
        Set<String> kSet = Stream.of(keys).map(k -> k).collect(Collectors.toSet());
        redisTemplate.delete(kSet);
    }

    /**
     * 设置key的生命周期,之前存储的时候是没有设置过期时间,现在重新设置过期时间
     *
     * @param key
     * @param time   过期时间-->为long类型
     * @param timeUnit     过期时间单位-->TimeUnit枚举
     */
    public void expireKey(String key, long time, TimeUnit timeUnit) {
        redisTemplate.expire(key, time, timeUnit);
    }

    /**
     * 指定key在指定的日期过期,指定在什么日期进行过期
     *
     * @param key
     * @param date
     */
    public void expireKeyAt(String key, Date date) {
        redisTemplate.expireAt(key, date);
    }

    /**
     * 查询key的生命周期
     *
     * @param key
     * @return
     */
    public long getKeyExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 将key设置为永久有效,之前存储的时候是没有设置过期时间,现在重新设置过期时间
     *
     * @param key
     */
    public void persistKey(String key) {
        redisTemplate.persist(key);
    }
}
