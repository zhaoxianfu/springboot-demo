package com.springboot.demo.util;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName:RedisKeyUtil
 * @Despriction: redis的key工具类
 * @Author:zhaoxianfu
 * @Date:Created 2019/3/27  17:22
 * @Version1.0
 **/

@Slf4j
public class RedisKeyUtil {
    /**
     * redis的key
     * 形式为：
     * 表名:主键名:主键值:列名
     *
     * @param tableName 表名
     * @param majorKey 主键名
     * @param majorKeyValue 主键值
     * @param column 列名
     * @return
     */
    public static String getKeyWithColumn(String tableName,String majorKey,String majorKeyValue,String column){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(tableName).append(":");
        stringBuffer.append(majorKey).append(":");
        stringBuffer.append(majorKeyValue).append(":");
        stringBuffer.append(column);
        return stringBuffer.toString();
    }
    /**
     * redis的key
     * 形式为：
     * 表名:主键名:主键值
     *
     * @param tableName 表名
     * @param majorKey 主键名
     * @param majorKeyValue 主键值
     * @return
     */
    public static String getKey(String tableName,String majorKey,String majorKeyValue){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(tableName).append(":");
        stringBuffer.append(majorKey).append(":");
        stringBuffer.append(majorKeyValue).append(":");
        return stringBuffer.toString();
    }
}
