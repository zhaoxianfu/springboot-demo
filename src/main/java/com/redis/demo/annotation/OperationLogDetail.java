package com.redis.demo.annotation;

import com.redis.demo.enums.OperationTypeEnum;
import com.redis.demo.enums.OperationUnitEnum;

import java.lang.annotation.*;

/**
 * @ClassName:OperationLogDetail
 * @Despriction: 记录日志操作的注解
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/17  12:40
 * @Version1.0
 **/

//@OperationLogDetail(detail = "通过手机号[{{tel}}]获取用户名",level = 3,operationUnit = OperationUnit.USER,operationType = OperationType.SELECT)
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationLogDetail {

    /**
     * 方法描述,可使用占位符获取参数:{{tel}}
     */
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
