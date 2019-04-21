package com.redis.demo.config;

import com.redis.demo.properties.DataJedisProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @ClassName:RedisConfig
 * @Despriction: reids相关bean的配置
 * @Author:zhaoxianfu
 * @Date:Created 2019/3/27  15:30
 * @Version1.0
 **/

@Slf4j
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    @Autowired
    private DataJedisProperties dataJedisProperties;

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    /**
     * @return
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        //  设置自动key的生成规则，配置spring boot的注解，进行方法级别的缓存
        // 使用：进行分割，可以很多显示出层级关系
        // 这里其实就是new了一个KeyGenerator对象，只是这是lambda表达式的写法，我感觉很好用，大家感兴趣可以去了解下
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(":");
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(":" + String.valueOf(obj));
            }
            String rsToUse = String.valueOf(sb);
            log.info("自动生成Redis Key -> [{}]", rsToUse);
            return rsToUse;
        };
    }

    /**
     * 初始化重写spring缓存的缓存管理器-->将spring缓存使用redis进行缓存
     *
     * @return
     */
    @Bean
    @Override
    public CacheManager cacheManager() {
        // 初始化缓存管理器，在这里我们可以缓存的整体过期时间什么的，我这里默认没有配置
        log.info("初始化 -> [{}]", "CacheManager RedisCacheManager Start");
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager
                .RedisCacheManagerBuilder
                .fromConnectionFactory(jedisConnectionFactory);
        return builder.build();
    }

    /**
     * 步骤一
     *
     * 创建jedis连接池配置类对象
     * redis是单进程和单线程的,是为了提前获取好连接,每次只能一个进行连接redis,提前连接好可以节省连接reidis的时间
     *
     * @return
     */
    @Bean
    public JedisPoolConfig redisPoolFactory() {
        log.info("JedisPool init successful，host -> [{}]；port -> [{}]", dataJedisProperties.getHost(), dataJedisProperties.getPort());
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(dataJedisProperties.getMaxIdle());
        jedisPoolConfig.setMaxWaitMillis(dataJedisProperties.getMaxWaitMillis());
        return jedisPoolConfig;
    }

    /**
     * 步骤二
     *
     * 创建jedis连接工厂类对象
     *
     * @return
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
        log.info("Create JedisConnectionFactory successful");

        //设置连接池配置类对象----传入连接池的配置信息
        JedisConnectionFactory factory = new JedisConnectionFactory(jedisPoolConfig);

//        Jedis jedis = factory.getShardInfo().createResource();
        factory.setHostName(dataJedisProperties.getHost());
        factory.setPort(dataJedisProperties.getPort());
        factory.setTimeout(dataJedisProperties.getTimeout());
        factory.setPassword(dataJedisProperties.getPassword());
        return factory;
    }

    /**
     * 步骤三
     *
     * RedisTemplate进行设置key和value的序列化方式,默认string类型是JDK的序列化方式
     *
     * @param jedisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {

        // 配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        //RedisTemplate会自动初始化StringRedisSerializer，所以这里直接获取
        RedisSerializer<String> stringSerializer = redisTemplate.getStringSerializer();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        //设置字符串序列化器,这样Spring就会把Redis的key当作字符串处理了
        redisTemplate.setKeySerializer(stringSerializer); // key序列化
        redisTemplate.setValueSerializer(stringSerializer); // value序列化
        redisTemplate.setHashKeySerializer(stringSerializer); // Hash key序列化
        redisTemplate.setHashValueSerializer(stringSerializer); // Hash value序列化
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * 步骤四
     *
     * 初始化StringRedisTemplate
     *
     * @param jedisConnectionFactory
     * @return
     */
    @Bean
    public StringRedisTemplate stringRedisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        //设置连接工厂对象
        stringRedisTemplate.setConnectionFactory(jedisConnectionFactory);

        RedisSerializer<String> stringSerializer = stringRedisTemplate.getStringSerializer();
        stringRedisTemplate.setKeySerializer(stringSerializer); // key序列化
        stringRedisTemplate.setValueSerializer(stringSerializer); // value序列化
        return stringRedisTemplate;
    }


    /**
     * 使用redis作为spring缓存中间件时,当Redis发生异常时,打印日志,程序正常执行
     *
     * @return
     */
    @Bean
    @Override
    public CacheErrorHandler errorHandler() {
        // 异常处理，当Redis发生异常时，打印日志，但是程序正常走
        log.info("初始化 -> [{}]", "Redis CacheErrorHandler");
        CacheErrorHandler cacheErrorHandler = new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
                log.error("Redis occur handleCacheGetError：key -> [{}]", key, e);
            }

            @Override
            public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
                log.error("Redis occur handleCachePutError：key -> [{}]；value -> [{}]", key, value, e);
            }

            @Override
            public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {
                log.error("Redis occur handleCacheEvictError：key -> [{}]", key, e);
            }

            @Override
            public void handleCacheClearError(RuntimeException e, Cache cache) {
                log.error("Redis occur handleCacheClearError：", e);
            }
        };
        return cacheErrorHandler;
    }

}

