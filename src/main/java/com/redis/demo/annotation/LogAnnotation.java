package com.redis.demo.annotation;

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

    String des() default "";
}
