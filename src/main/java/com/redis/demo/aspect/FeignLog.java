package com.redis.demo.aspect;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Stopwatch;
import com.redis.demo.model.ResultBase;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.rx.common.App;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Slf4j
@Aspect
@Component
@ConditionalOnProperty(prefix = "log.feign", name = "enabled", havingValue = "true", matchIfMissing = false)
public class FeignLog {

    /**
     * 切入点表达式
     */
    @Pointcut("@within(org.springframework.cloud.openfeign.FeignClient)")
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
        RequestMapping methodMapping = method.getAnnotation(RequestMapping.class);
        if (methodMapping == null) {
            return joinPoint.proceed();
        }

        String lineSeparator = System.lineSeparator();
        StringBuilder logContent = new StringBuilder(lineSeparator);

        // 2.1 从线程栈中取得调用接口的类及方法
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (int i = 2; i < stackTraceElements.length; i++) {
            StackTraceElement stackTraceElement = stackTraceElements[i];
            if (stackTraceElement.getClassName().startsWith("com.zhongan.graphene")) {
                logContent
                        .append(String.format("Feign ClassName\t%s.%s[LineNumber:%s]", stackTraceElement.getClassName(),
                                stackTraceElement.getMethodName(), stackTraceElement.getLineNumber()));
                logContent.append(lineSeparator);
                break;
            }
        }

        // 2.2 获取接口的类及方法名
        logContent.append(
                String.format("Feign Interface\t%s.%s", method.getDeclaringClass().getName(), method.getName()));
        logContent.append(lineSeparator);

        // 2.3 拼装接口URL
        // 2.3.1 获取Class上的FeignURL
        String url = "";
        FeignClient feignClient = null;
        for (Class<?> pi : joinPoint.getTarget().getClass().getInterfaces()) {
            if ((feignClient = pi.getAnnotation(FeignClient.class)) != null) {
                break;
            }
        }
        if (feignClient != null) {
            String baseUrl = feignClient.url();
            int s = baseUrl.indexOf("${"), e = baseUrl.indexOf("}");
            if (s != -1 && e != -1) {
                String n = baseUrl.substring(s + 2, e);
                Object v = App.readSetting(n);
                if (v != null) {
                    baseUrl = String.valueOf(v);
                }
            }
            url += baseUrl;
        }

        // 2.3.2 获取Class RequestMapping URL
        RequestMapping baseMapping = method.getDeclaringClass().getAnnotation(RequestMapping.class);
        Function<RequestMapping, String> pf = p -> String.join(",",
                ArrayUtils.isNotEmpty(p.value()) ? p.value() : p.path());
        if (baseMapping != null) {
            url += pf.apply(baseMapping);
        }

        // 2.3.3 获取方法的 ReqeustMapping URL
        if (null != methodMapping) {
            url += pf.apply(methodMapping);
        }

        logContent.append(String.format("Feign Url：\t%s", url + lineSeparator));
        logContent.append(String.format("Feign 入参:\t%s", toString(joinPoint.getArgs())));
        try {
            Stopwatch watcher = Stopwatch.createStarted();
            Object r = joinPoint.proceed();
            long ms = watcher.elapsed(TimeUnit.MILLISECONDS);
            logContent.append(String.format("\tElapsed = %sms", ms)).append(lineSeparator);
            logContent.append(String.format("Feign 返回:\t%s", toString(r)));
            return r;
        } catch (Exception e) {
            logContent.append(String.format("Error:\t%s", e.getMessage()));
            return onException(log, e);
        } finally {
            log.info(logContent.toString() + lineSeparator);
        }
    }

    private String toString(Object... args) {

        if (ArrayUtils.isNotEmpty(args)) {
            //            if (args[0] instanceof FileRequestDTO) {
            //                FileRequestDTO requestDTO = (FileRequestDTO) args[0];
            //                JSONObject json = JSON.parseObject(JSON.toJSONString(requestDTO));
            //                if (requestDTO.getContent() != null) {
            //                    json.put("content", "[bytesLength=" + requestDTO.getContent().length + "]");
            //                }
            //                return json.toJSONString();
            //            } else if (args[0] instanceof ResultBase) {
            //                ResultBase resultBase = (ResultBase) args[0];
            //                if (resultBase.getValue() instanceof FileResponseDTO) {
            //                    FileResponseDTO responseDTO = (FileResponseDTO) resultBase.getValue();
            //                    JSONObject json = JSON.parseObject(JSON.toJSONString(responseDTO));
            //                    if (responseDTO.getContent() != null) {
            //                        json.put("content", "[bytesLength=" + responseDTO.getContent().length + "]");
            //                    }
            //                    return json.toJSONString();
            //                }
            //            }
        }
        return JSON.toJSONString(args);
    }

    private Object onException(org.slf4j.Logger log, Exception e) {
        log.error("Remote service error {}", e);

        ResultBase errorResult = new ResultBase<>();
        errorResult.setSuccess(false);
        errorResult.setMsg(e.getMessage());
        return errorResult;
    }
}
