package com.redis.demo.aspect;

import com.alibaba.fastjson.JSON;
import com.redis.demo.annotation.OperationLogDetail;
import com.redis.demo.model.OperationLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName:LogAspect
 * @Despriction: 日志切面第一种
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/17  12:38
 * @Version1.0
 **/
@Component
@Aspect
@Order(1)
@Slf4j
@ConditionalOnProperty(prefix = "log.logAspect1", name = "enabled", havingValue = "true", matchIfMissing = false)
public class LogAspect1 {

    /**
     * 此处的切点是注解的方式，也可以用包名的方式达到相同的效果
     * '@Pointcut("execution(* com.wwj.springboot.service.impl.*.*(..))")'
     */
    @Pointcut("@annotation(com.redis.demo.annotation.OperationLogDetail)")
    public void operationLog() {
    }

    /**
     * 环绕增强，相当于MethodInterceptor
     */
    @Around("operationLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {

        Object res = null;
        long time = System.currentTimeMillis();
        long intervalTime = 0;
        try {
            res = joinPoint.proceed();
            intervalTime = System.currentTimeMillis() - time;
            return res;
        } finally {
            try {
                //方法执行完成后增加日志
                addOperationLog(joinPoint, res, intervalTime);
            } catch (Exception e) {
                System.out.println("LogAspect 操作失败：" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * 增加日志method
     *
     * @param joinPoint
     * @param res
     * @param intervalTime
     */
    private void addOperationLog(JoinPoint joinPoint, Object res, long intervalTime) {

        //这个连接点方法相关的参数
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        log.info("目标方法名为:" + signature.getName());
        log.info("目标方法所属类的简单类名:" + signature.getDeclaringType().getSimpleName());
        log.info("目标方法所属类的类名:" + signature.getDeclaringTypeName());
        log.info("目标方法声明类型:" + signature.getModifiers());

        OperationLog operationLog = new OperationLog();

        //设置这个连接点的运行时间
        operationLog.setRunTime(intervalTime);
        //设置这个连接点的返回值数据
        operationLog.setReturnValue(JSON.toJSONString(res));
        //设置这个日志记录对象的主键id
        operationLog.setId(UUID.randomUUID().toString());
        //获取这个连接点的入参
        operationLog.setArgs(JSON.toJSONString(joinPoint.getArgs()));
        //设置日志记录对象的创建时间
        operationLog.setCreateTime(new Date());
        //设置这个日志对象的方法名
        operationLog.setMethod(signature.getDeclaringTypeName() + "." + signature.getName());
        operationLog.setUserId("#{currentUserId}");
        operationLog.setUserName("#{currentUserName}");

        //获取连接点上的注解
        OperationLogDetail annotation = signature.getMethod().getAnnotation(OperationLogDetail.class);

        if (null != annotation) {
            operationLog.setLevel(annotation.level());
            operationLog.setDescribe(getDetail(((MethodSignature) joinPoint.getSignature()).getParameterNames(), joinPoint.getArgs(), annotation));
            operationLog.setOperationType(annotation.operationTypeEnum().getValue());
            operationLog.setOperationUnit(annotation.operationUnitEnum().getValue());
        }
        //TODO 这里保存日志
        log.info("记录日志:{}", operationLog.toString());
// operationLogService.insert(operationLog);
    }

    /**
     * 对当前登录用户和占位符处理
     *
     * @param argNames   方法参数名称数组
     * @param args       方法参数数组
     * @param annotation 注解信息
     * @return 返回处理后的描述
     */
    private String getDetail(String[] argNames, Object[] args, OperationLogDetail annotation) {
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

    /**
     * 前置通知
     *
     * @param joinPoint
     */
    @Before("operationLog()")
    public void doBeforeAdvice(JoinPoint joinPoint) {
        log.info("进入方法前执行.....");
    }

    /**
     * 处理完请求，返回内容
     *
     * @param ret
     */
    @AfterReturning(returning = "ret", pointcut = "operationLog()")
    public void doAfterReturning(Object ret) {
        log.info("方法的返回值 : " + ret);
    }

    /**
     * 后置异常通知
     *
     * @param jp
     */
    @AfterThrowing("operationLog()")
    public void throwss(JoinPoint jp) {
        log.info("方法异常时执行.....");
    }

    /**
     * 后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
     *
     * @param jp
     */
    @After("operationLog()")
    public void after(JoinPoint jp) {
        log.info("方法最后执行.....");
    }

}
