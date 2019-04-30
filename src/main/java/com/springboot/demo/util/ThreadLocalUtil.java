package com.springboot.demo.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName:ThreadLocalUtil
 * @Despriction:  TreadLocal全局上下文工具类
 * @Author:
 * @Date:Created 2019/4/23  15:53
 * @Version1.0
 **/

/**
 * @Despriction
 * 线程本地对象,在线程对象里面存储一个threadLocalMap,key为当前的线程对象,value为我们存储的值,当我们存储的时候用threadLocal进行存储,threadLocal
 * 只是存储的变量的副本,其实存储在threadLocalMap里面,如果我们想要存储更多的数据话,最好把一个Map作为value,
 * 如果不这样的话,在一个线程中后面set的值会把前面的值给覆盖掉
 * @author zhaoxianfu
 */
public class ThreadLocalUtil {

    /**
     * 用final修饰的话,就不能改变这个引用的地址了
     */
    private static final ThreadLocal<Map<Object, Object>> threadLocalContext = new ThreadLocal<Map<Object, Object>>() {
        /**
         * 初始化一个存储map类型的threadLocalMap
         *
         * @return
         */
        @Override
        protected Map<Object, Object> initialValue() {
            return new HashMap<Object, Object>(16);
        }
    };

    /**
     * 根据key获取值
     *
     * @param key
     * @return
     */
    public static Object getValue(Object key) {
        if(threadLocalContext.get() == null) {
            return null;
        }
        return threadLocalContext.get().get(key);
    }

    /**
     * 存储值
     *
     * @param key
     * @param value
     * @return
     */
    public static Object setValue(Object key, Object value) {
        Map<Object, Object> cacheMap = threadLocalContext.get();
        if(cacheMap == null) {
            cacheMap = new HashMap<Object, Object>();
            threadLocalContext.set(cacheMap);
        }
        return cacheMap.put(key, value);
    }

    /**
     * 根据key移除值
     *
     * @param key
     */
    public static void removeValue(Object key) {
        Map<Object, Object> cacheMap = threadLocalContext.get();
        if(cacheMap != null) {
            cacheMap.remove(key);
        }
    }

    /**
     * 重置线程本地对象里面的threadLocalMap里面的value
     */
    public static void reset() {
        if(threadLocalContext.get() != null) {
            threadLocalContext.get().clear();
        }
    }

}
