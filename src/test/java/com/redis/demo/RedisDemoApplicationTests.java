package com.redis.demo;

import com.redis.demo.pojo.User;
import com.redis.demo.redis.RedisService;
import com.redis.demo.util.RedisKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import java.util.*;
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
    @Autowired
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
     * redis上的事务-->如果监控的值发生变化了,那就会取消事务,不执行了,
     * <p>
     * Redis事务和数据库事务的不一样,对于 Redis事务是先让命令进入队列,所以一开始它并没有检测这个加一命令是否能够成功,
     * 只有在exec命令执行的时候,才能发现错误,对于出错的命令 Redis只是报出错误,而错误后面的命令依旧被 行,所以key2和 ey3都存在数据,这就是Redis事务的特点,也是使用Redis事务需要特别注意的地方。
     * 为了克服这个问题,一般我们要在执行 Redis 事务前,严格地检查数据,以避免这样的情况发生.
     *
     * @throws Exception
     */
    @Test
    public void testRedisTransation() throws Exception {
        //首先在key上设值为key1
        redisTemplate.opsForValue().set("keyl", "value1");

        //可以在同一条连接下执行多个Redis命令
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {

                //设置监控key1
                operations.watch("key1");
                //开启事务,在exec命令开始前,全部都只是进入对列,而不是真正的去执行
                operations.multi();
                operations.opsForValue().set("key2", "value2");

                operations.opsForValue().increment("key1", 1);

                //获取的值姜维null,因为Redis只是把命令放入了队列中
                Object key2 = operations.opsForValue().get("key2");
                System.out.println(key2);

                operations.opsForValue().set("key3", "value3");
                Object key3 = operations.opsForValue().get("key3");
                System.out.println(key3);

                //执行exec命令,将先判别监控的key1在watch到exec之间有没有被修改过,如果修改过,就不执行Redis里面的事务内的命令,否则执行事务
                return operations.exec();
            }
        });
    }

    /**
     * redis流水线操作-->同时执行很多命令
     *
     * @throws Exception
     */
    @Test
    public void testExecutePipelined() throws Exception {
        Long start = System.currentTimeMillis();
        List list = (List) redisTemplate.executePipelined(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                for (int i = 0; i < 10000; i++) {
                    operations.opsForValue().set("pipeline" + i, "value" + i);
                    String s = (String) operations.opsForValue().get("pipeline" + i);
                    if (10000 == i) {
                        log.info("命令全部进入了队列中");
                    }
                }
                return null;
            }

        });
        log.info("执行的所有数据为一个List:" + list.toString());
        Long end = System.currentTimeMillis();
        log.info("耗时:" + (end - start) + "毫秒");
    }

    /**
     * 使用同一个连接进行多个操作,节省连接---使用SessionCallback进行
     *
     * @throws Exception
     */
    @Test
    public void useOneConnectionOpt() throws Exception {
        //可以在同一条连接下执行多个Redis命令
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                //下面两个操作使用的是同一个连接进行的操作----如果不使用SessionCallback的话,就会分别使用一个连接
                operations.opsForValue().set("key1", "加油");
                operations.opsForHash().put("hash", "filed1", "加油");
                return null;
            }
        });
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

        log.info("redisOperations对象是RedisTemplate产生的吗?---->{}", flag);   //True
    }

    /**
     * valueOperations
     * set方法进行设置key的数据
     * <p>
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
     * 增操作
     *
     * @throws Exception
     */
    @Test
    public void incrOperation() throws Exception {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        
        valueOperations.set("increase1", "1");

        //获取的是增加后的值
        Long increase1 = valueOperations.increment("increase1");
        log.info(increase1 + "");
    }

    /**
     * 减操作 ---redisTemplate上是不支持,所以需要原生的jedis进行支持
     *
     * @throws Exception
     */
    @Test
    public void decrOperation() throws Exception {
        ValueOperations valueOperations = redisTemplate.opsForValue();

        valueOperations.set("key", "1");

        valueOperations.increment("key", 1);

        Jedis jedis = (Jedis) redisTemplate.getConnectionFactory().getConnection().getNativeConnection();

        //减1操作
        jedis.decr("key");
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
     * 往一个value为list的集合中左边添加一个元素
     *
     * @throws Exception
     */
    @Test
    public void ListOperationsPush() throws Exception {
        User User = new User();
        User.setAddress("日本");
        User.setName("jantent");
        User.setAge(22);

        //在key为list:user的list元素对象的左边添加值,就是序号最小添加
        listOperations.leftPush("list:user", User);

        //获取key为list:user的值list集合对象的第一个元素的值
        Object indexObject = listOperations.index("list:user", 0);
        System.out.println(indexObject);
    }

    /**
     * listOperations
     * <p>
     * 往一个value为list的集合中获取list集合索引下具体一个元素的值
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
     * 往一个value为list的集合中对list集合下一个索引下元素重新赋值
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
     * 获取这个value下的lsit集合对象的数量
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
     * 获取这个value的list集合中一定范围的list对象信息
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
     * 获取这个value的list集合所有元素对象
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
     * 移除这个value的list集合中的左边第一个元素
     *
     * @throws Exception
     */
    @Test
    public void ListOperationsPop() throws Exception {
        listOperations.leftPop("list:user");
    }

    /**
     * hashOperations
     * <p>
     * 对hash的数据进行赋值操作
     * <p>
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
     * <p>
     * 获取这个hash对象中的Key为"hash:user"的map对象
     *
     * @throws Exception
     */
    @Test
    public void HashOperationMap() throws Exception {
        Map<String, Object> objectMap = hashOperations.entries("hash:user");
        System.out.println(objectMap);
    }

    /**
     * hashOperations
     * <p>
     * 获取这个hash对象中的key为"hash:user"的map对象中的所有的hashKey(set集合)
     *
     * @throws Exception
     */
    @Test
    public void HashOperationKeys() throws Exception {
        Set<String> keys = hashOperations.keys("hash:user");
        System.out.println(keys);
    }

    /**
     * hashOperations
     * <p>
     * 直接往hash对象的key中直接增加一个map对象
     *
     * @throws Exception
     */
    @Test
    public void HashOperationPutAll() throws Exception {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("2", 2424224);
        hashMap.put("3", "asfwegwfwegf");
        hashMap.put("2wr", "asgfwegfewsg");

        hashOperations.putAll("hash:mapValue:111", hashMap);
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
