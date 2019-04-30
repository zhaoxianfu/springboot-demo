package com.springboot.demo.aspect;

import com.alibaba.fastjson.JSON;
import com.springboot.demo.annotation.LogAnnotation;
import com.springboot.demo.model.OperationLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName:LogAspect2
 * @Despriction: 日志切面第二种
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/17  19:37
 * @Version1.0
 **/

@Slf4j
@Component
@Aspect
@Order(2)
@ConditionalOnProperty(prefix = "log.logAspect2", name = "enabled", havingValue = "true", matchIfMissing = false)
public class LogAspect2 {

    /**
     * 切入点表达式
     */
    @Pointcut("@annotation(com.springboot.demo.annotation.LogAnnotation)")
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
        OperationLog operationLog = new OperationLog();

        String lineSeparator = System.lineSeparator();
        StringBuilder stringBuilder = new StringBuilder();

        try {
            stringBuilder.append("开始执行连接点方法");
            stringBuilder.append(lineSeparator);

            //获取连接点的类名
            stringBuilder.append(String.format("类名：\t%s ", proceedingJoinPoint.getTarget().getClass().getName()));
            //另外一种获取连接点类名的方式
            //stringBuilder.append(String.format("类名：\t%s ", proceedingJoinPoint.getSignature().getDeclaringTypeName()));
            stringBuilder.append(lineSeparator);
            //这个连接点方法相关的参数
            MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();

            stringBuilder.append(String.format("方法名：\t%s ", signature.getMethod().getName()));
            stringBuilder.append(lineSeparator);

            //获取连接点的入参
            Object[] args = proceedingJoinPoint.getArgs();

            for (int i = 0; i < args.length; i++) {
                stringBuilder.append(String.format("参数" + (i + 1) + "：\t%s ", JSON.toJSON(args[i])));
                stringBuilder.append(lineSeparator);
            }

            //获取连接点方法上的注解,进而获取注解上的属性,当然也可以通过把注解直接加到切面作为参数传递进来
            LogAnnotation annotation = signature.getMethod().getAnnotation(LogAnnotation.class);

            if (null != annotation) {
                //设置这个日志记录对象的主键id
                operationLog.setId(UUID.randomUUID().toString());
                //设置日志记录对象的创建时间----日期json化的时候会转为毫秒值
                operationLog.setCreateTime(new Date());
                operationLog.setLevel(annotation.level());
                operationLog.setDescribe(getDetail(((MethodSignature) proceedingJoinPoint.getSignature()).getParameterNames(), proceedingJoinPoint.getArgs(), annotation));
                operationLog.setOperationType(annotation.operationTypeEnum().getValue());
                operationLog.setOperationUnit(annotation.operationUnitEnum().getValue());
            }

            stringBuilder.append(String.format("操作日志Json：\t%s ", JSON.toJSON(operationLog)));
            stringBuilder.append(lineSeparator);

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

    /**
     * 对当前登录用户和占位符处理
     *
     * @param argNames   方法参数名称数组
     * @param args       方法参数数组
     * @param annotation 注解信息
     * @return 返回处理后的描述
     */
    private String getDetail(String[] argNames, Object[] args, LogAnnotation annotation) {
        Map<Object, Object> map = new HashMap<>(4);
        for (int i = 0; i < argNames.length; i++) {
            map.put(argNames[i], args[i]);
        }
        String detail = annotation.detail();
        try {
            detail = "'" + "#{currentUserName}" + "'=》" + annotation.detail();
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                Object k = entry.getKey();
                Object v = entry.getValue();
                detail = detail.replace("{{" + k + "}}", JSON.toJSONString(v));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

}
