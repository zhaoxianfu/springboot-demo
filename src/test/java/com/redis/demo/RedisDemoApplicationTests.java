package com.redis.demo;

import com.redis.demo.config.RedisService;
import com.redis.demo.pojo.User;
import com.redis.demo.util.RedisKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 测试整合spring
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisDemoApplication.class)
@Slf4j
public class RedisDemoApplicationTests {

    /**
     * redis中key和value都为string类型的模板类对象
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * redis操作模板类对象
     */
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 对redis字符串类型数据操作
     */
    @Resource
    private ValueOperations<String, Object> valueOperations;

    /**
     * 对hash类型的数据操作
     */
    @Autowired
    private HashOperations<String, String, Object> hashOperations;

    /**
     * 对链表类型的数据操作
     */
    @Autowired
    private ListOperations<String, Object> listOperations;

    /**
     * 对无序集合类型的数据操作
     */
    @Autowired
    private SetOperations<String, Object> setOperations;

    /**
     * 对有序集合类型的数据操作
     */
    @Autowired
    private ZSetOperations<String, Object> zSetOperations;

    /**
     * redis中key和value都为string类型时的操作的工具类
     */
    @Autowired
    private RedisService redisService;

    @Before
    public void beforeMethod() {
        log.info("------------------------------------------->");
        log.info("Redis的测试方法开始进行");
    }

    @After
    public void afterMethod() {
        log.info("------------------------------------------->");
        log.info("Redis的测试方法已结束");
    }

    /**
     * 获取操作的模板类对象-->通过已经通过操作模板类对象获取的各种类型的操作对象来获取这个操作模板类对象
     *
     * @throws Exception
     */
    @Test
    public void testGetTemplateObject() throws Exception {

        //获取的是谁获取的hashOperations这个对象-->获取是谁产生它的--->也就是RedisTemplate
        RedisOperations<String, ?> redisOperations = hashOperations.getOperations();
        boolean flag = redisOperations instanceof RedisTemplate;

        log.info("redisOperations对象是RedisTemplate产生的吗?---->{}",flag);   //True
    }

    /**
     * valueOperations
     * set方法进行设置key的数据
     *
     * set方法会重置之前里面的数据(如果之前存在key)--->类似与map中的put方法
     * set的时候不设置过期时间,默认是永久进行存储的----需要设置过期时间才会设置
     * setIfAbsent如果不存在就设置数据
     * setIfPresent如果存在就设置数据
     *
     * @throws Exception
     */
    @Test
    public void testObj() throws Exception {
        User user = new User();
        user.setAddress("上海");
        user.setName("测试dfas");
        user.setAge(12355);

        ValueOperations<String, Object> operations = redisTemplate.opsForValue();

        String key = RedisKeyUtil.getKey(User.Table, "name", user.getName());
        operations.set(key, user, 10, TimeUnit.MINUTES);

        log.info("存入的user的信息为{}", user);
    }

    /**
     * valueOperations
     * <p>
     * 通过模板类对象直接操作key的过期时间---泛型一定要对应,否则
     *
     * @throws Exception
     */
    @Test
    public void testObj1() throws Exception {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        redisTemplate.expire("t_user:name:测试dfas:", 50, TimeUnit.MINUTES);
    }

    /**
     * valueOperations
     * <p>
     * set的时候不设置过期时间,默认是永久进行存储的----需要设置过期时间才会设置
     *
     * @throws Exception
     */
    @Test
    public void testValueOption() throws Exception {
        User User = new User();
        User.setAddress("上海");
        User.setName("jantent");
        User.setAge(23);
        valueOperations.set("test", User);

        log.info("这次请求存储的值为{}", valueOperations.get("test"));
    }

    /**
     * listOperations
     * <p>
     * 往一个key为list的集合中左边添加一个元素
     *
     * @throws Exception
     */
    @Test
    public void ListOperationsPush() throws Exception {
        User User = new User();
        User.setAddress("日本");
        User.setName("jantent");
        User.setAge(22);

        //在kry为list:user的list元素对象的左边添加值,就是序号最小添加
        listOperations.leftPush("list:user", User);

        //获取key为list:user的值list集合对象的第一个元素的值
        Object indexObject = listOperations.index("list:user", 0);
        System.out.println(indexObject);
    }

    /**
     * listOperations
     * <p>
     * 往一个key为list的集合中获取list集合索引下具体一个元素的值
     *
     * @throws Exception
     */
    @Test
    public void ListOperationsGet() throws Exception {
        Object indexObject = listOperations.index("list:user", 0);
        System.out.println(indexObject);
    }

    /**
     * listOperations
     * <p>
     * 往一个key为list的集合中对list集合下一个索引下元素重新赋值
     *
     * @throws Exception
     */
    @Test
    public void ListOperationsPushUpdate() throws Exception {
        listOperations.set("list:user", 0, 111);

        System.out.println(listOperations.index("list:user", 0));
    }

    /**
     * listOperations
     * <p>
     * 获取这个Key下的lsit集合对象的数量
     *
     * @throws Exception
     */
    @Test
    public void ListOperationsCount() throws Exception {
        Long size = listOperations.size("list:user");
        System.out.println(size);
    }

    /**
     * listOperations
     * <p>
     * 获取这个key的list集合中一定范围的list对象信息
     *
     * @throws Exception
     */
    @Test
    public void ListOperationsRange() throws Exception {

        //左包含,右边也包含
        List<Object> objectList = listOperations.range("list:user", 0, 1);

        System.out.println(objectList);
    }

    /**
     * listOperations
     * <p>
     * 获取这个key的list集合所有元素对象
     *
     * @throws Exception
     */
    @Test
    public void ListOperationsTotalList() throws Exception {
        Long size = listOperations.size("list:user");
        if (null != size) {
            List<Object> objectList = listOperations.range("list:user", 0, size - 1);

            System.out.println(objectList);
        }
    }

    /**
     * listOperations
     * <p>
     * 移除这个key的list集合中的左边第一个元素
     *
     * @throws Exception
     */
    @Test
    public void ListOperationsPop() throws Exception {
        listOperations.leftPop("list:user");
    }

    /**
     * hashOperations
     *
     * 对hash的数据进行赋值操作
     *
     * key为这个map对象的key,hashKey为这个map对象中增加key和value的值
     *
     * @throws Exception
     */
    @Test
    public void HashOperations() throws Exception {
        User User = new User();
        User.setAddress("北京");
        User.setName("jantent");
        User.setAge(23);
        hashOperations.put("hash:user", "1", User);

        //获取这个key下的map对象中的hashkey为1的存储的对象
        System.out.println(hashOperations.get("hash:user", "1"));
    }

    /**
     * hashOperations
     *
     * 获取这个hash对象中的Key为"hash:user"的map对象
     * @throws Exception
     */
    @Test
    public void HashOperationMap() throws Exception {
        Map<String, Object> objectMap = hashOperations.entries("hash:user");
        System.out.println(objectMap);
    }

    /**
     * hashOperations
     *
     * 获取这个hash对象中的key为"hash:user"的map对象中的所有的hashKey(set集合)
     * @throws Exception
     */
    @Test
    public void HashOperationKeys() throws Exception {
        Set<String> keys = hashOperations.keys("hash:user");
        System.out.println(keys);
    }

    /**
     * hashOperations
     *
     * 直接往hash对象的key中直接增加一个map对象
     * @throws Exception
     */
    @Test
    public void HashOperationPutAll() throws Exception {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("2",2424224);
        hashMap.put("3","asfwegwfwegf");
        hashMap.put("2wr","asgfwegfewsg");

        hashOperations.putAll("hash:mapValue:111",hashMap);
        System.out.println(hashOperations.entries("hash:mapValue:111"));
    }

//获取带bound的操作那五种类型数据的对象-->必须要先指明key

    @Test
    public void getBoundOpertionObiect() throws Exception {
        BoundValueOperations boundValueOperations = redisTemplate.boundValueOps("1");
        BoundListOperations boundListOperations = redisTemplate.boundListOps("1");
        BoundHashOperations boundHashOperations = redisTemplate.boundHashOps("1");
        BoundSetOperations boundSetOperations = redisTemplate.boundSetOps("1");
        BoundZSetOperations boundZSetOperations = redisTemplate.boundZSetOps("1");
    }


    @Test
    public void testSetOperation() throws Exception {
        User User = new User();
        User.setAddress("北京");
        User.setName("jantent");
        User.setAge(23);
        User auserVo = new User();
        auserVo.setAddress("n柜昂周");
        auserVo.setName("antent");
        auserVo.setAge(23);
        setOperations.add("user:test", User, auserVo);
        Set<Object> result = setOperations.members("user:test");
        System.out.println(result);
    }

}
