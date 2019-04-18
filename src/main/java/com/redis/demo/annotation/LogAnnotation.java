package com.redis.demo.annotation;

import com.redis.demo.enums.OperationTypeEnum;
import com.redis.demo.enums.OperationUnitEnum;

import java.lang.annotation.*;

/**
 * @ClassName:LogAnnotation
 * @Despriction:
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/17  20:16
 * @Version1.0
 **/

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnnotation {

    String detail() default "";

    /**
     * 日志等级:自己定，此处分为1-9
     */
    int level() default 0;

    /**
     * 操作类型(enum):主要是select,insert,update,delete
     */
    OperationTypeEnum operationTypeEnum() default OperationTypeEnum.UNKNOWN;

    /**
     * 被操作的对象(此处使用enum):可以是任何对象，如表名(user)，或者是工具(redis)
     */
    OperationUnitEnum operationUnitEnum() default OperationUnitEnum.UNKNOWN;
}
