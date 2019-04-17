package com.redis.demo.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @ClassName:LogAspect2
 * @Despriction: 日志切面第二种
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/17  19:37
 * @Version1.0
 **/

@Component
@Aspect
@Order(2)
@Slf4j
@ConditionalOnProperty(prefix = "log.logAspect2", name = "enabled", havingValue = "true", matchIfMissing = false)
public class LogAspect2 {

    /**
     * 切入点表达式
     */
    @Pointcut("@annotation(com.redis.demo.annotation.LogAnnotation)")
    public void log() {
    }

    /**
     * 环绕通知
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = "log()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object result = null;
        String lineSeparator = System.lineSeparator();
        StringBuilder stringBuilder = new StringBuilder();

        try {
            stringBuilder.append("开始执行连接点方法");
            stringBuilder.append(lineSeparator);

            stringBuilder.append(String.format("类名：\t%s ", proceedingJoinPoint.getTarget().getClass().getName()));
            stringBuilder.append(lineSeparator);
            //这个连接点方法相关的参数
            MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

            stringBuilder.append(String.format("方法名：\t%s ", methodSignature.getMethod().getName()));
            stringBuilder.append(lineSeparator);

            //获取连接点的入参
            Object[] args = proceedingJoinPoint.getArgs();

            for (int i = 0; i < args.length; i++) {
                stringBuilder.append(String.format("参数" + (i + 1) + "：\t%s ", JSON.toJSON(args[i])));
                stringBuilder.append(lineSeparator);
            }
            //开始执行时间
            long startTime = System.currentTimeMillis();

            //连接点方法执行
            result = proceedingJoinPoint.proceed();

            //方法执行完时间
            long endTime = System.currentTimeMillis();

            stringBuilder.append(String.format("返回: \t%s ", JSON.toJSON(result)));
            stringBuilder.append(lineSeparator);

            stringBuilder.append(String.format("耗时：\t%s", (endTime - startTime) + "毫秒"));
            stringBuilder.append(lineSeparator);

        } catch (Exception e) {
            stringBuilder.append(String.format("异常：\t%s", e.getMessage()));
            stringBuilder.append(lineSeparator);
        } finally {
            log.info(stringBuilder.toString());
        }
        return result;
    }

}
