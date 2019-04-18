package com.redis.demo.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.redis.demo.model.ResultBase;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.function.Function;

/**
 * controller层的日志对象
 */

@Slf4j
@Aspect
@Component
@ConditionalOnProperty(prefix = "log.provider", name = "enabled", havingValue = "true", matchIfMissing = false)
public class ProviderLog {

    /**
     * 切入点表达式
     */
    @Pointcut("@within(org.springframework.stereotype.Controller)")
    public void log() {
    }

    @Around("log()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {

        // 1 先过滤出有RequestMapping的方法
        Signature signature = joinPoint.getSignature();
        if (!(signature instanceof MethodSignature)) {
            return joinPoint.proceed();
        }
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        String lineSeparator = System.lineSeparator();
        StringBuilder stringBuilder = new StringBuilder(lineSeparator);

        // 2.1 从线程栈中取得调用接口的类及方法
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        // 2.2 获取接口的类及方法名
        stringBuilder.append(String.format("Provider Class:\t%s.%s", method.getDeclaringClass().getName(), method.getName()));
        stringBuilder.append(lineSeparator);

        // 2.3 拼装接口URL
        // 2.3.1 获取FeignURL

        String url = "";

        Function<RequestMapping, String> reqMappingFunc = p -> String.join(",",
                ArrayUtils.isNotEmpty(p.value()) ? p.value() : p.path());

        // 2.3.2 获取RequestMapping URL
        RequestMapping methodReqMapping = method.getAnnotation(RequestMapping.class);
        if (methodReqMapping != null) {
            url += reqMappingFunc.apply(methodReqMapping);
        }

        stringBuilder.append(String.format("Provider URL：\t\t%s", url + lineSeparator));
        stringBuilder.append(String.format("Provider 入参:\t%s", JSON.toJSONString(joinPoint.getArgs()) + lineSeparator));
        try {
            Object r = onProcess(joinPoint, stringBuilder);
            stringBuilder.append(String.format("Provider 返回:\t%s", JSON.toJSONString(r)));
            stringBuilder.append(lineSeparator);
            return r;
        } catch (Exception e) {
            stringBuilder.append(String.format("Error:\t\t%s", e.getMessage()));
            stringBuilder.append(lineSeparator);
            return onException(log, e);
        } finally {
            log.info(stringBuilder.toString(), SerializerFeature.DisableCircularReferenceDetect);
        }
    }

    private Object onProcess(ProceedingJoinPoint joinPoint, StringBuilder msg) throws Throwable {
        return joinPoint.proceed();
    }

    private Object onException(org.slf4j.Logger log, Exception e) {
        log.error("Remote service error {}", e);

        ResultBase errorResult = new ResultBase<>();
        errorResult.setSuccess(false);
        errorResult.setMsg(e.getMessage());
        return errorResult;
    }
}
