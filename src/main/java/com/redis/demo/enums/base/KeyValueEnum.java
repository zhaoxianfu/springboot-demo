package com.redis.demo.enums.base;

import java.io.Serializable;

/**
 * @ClassName:KeyValueEnum
 * @Despriction:
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/17  21:34
 * @Version1.0
 **/

public interface KeyValueEnum extends Serializable {

    Integer getCode();

    String getValue();

}
