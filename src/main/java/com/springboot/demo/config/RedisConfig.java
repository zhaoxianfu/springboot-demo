package com.springboot.demo.config;

import com.springboot.demo.properties.DataJedisProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

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
        // 初始化缓存管理器,在这里我们可以缓存的整体过期时间什么的
        log.info("初始化RedisCacheManager缓存管理器 ------> [{}]", "Init RedisCacheManager");

        //redis加锁的写入器
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.lockingRedisCacheWriter(jedisConnectionFactory);

        //启动redis缓存的默认配置
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        //设置JDK序列化器
        config = config.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new JdkSerializationRedisSerializer()));

        //使用前缀
        config.usePrefix();
        log.info("spring缓存中缓存key使用追加前缀");

        //设置key的前缀
        config.prefixKeysWith("redis110");
        log.info("设置spring缓存的key前缀为redis110");

        //设置10分钟超时
        config.entryTtl(Duration.ofMinutes(10));

        log.info("设置spring缓存的超时时间为{}",Duration.ofMinutes(10));
        //设置cacheName集合,缓存的前缀由cacheName:前缀:方法上定义的key
        String[] cacheNameArray = {"redisCache"};

        log.info("创建redisCacheManager上的所有的cacheName为{}",cacheNameArray);
        //创建缓存redisCacheManager缓存管理器
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisCacheWriter, config, cacheNameArray);
        return redisCacheManager;
    }

    /**
     * 步骤一
     * <p>
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
     * <p>
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
     * <p>
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
     * <p>
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

