package com.redis.demo.config;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.redis.demo.model.ResultBase;
import com.redis.demo.validator.UserValidator;
import feign.FeignException;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName:GlobalControllerExceptionHandler
 * @Despriction: 全局异常处理类和初始化绑定验证器
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/27  21:16
 * @Version1.0
 **/

@Slf4j
@ControllerAdvice    //对加入@Controller注解的类处理器进行统一异常处理或者绑定验证器或者初始化属性及其值。
//对于@RestController注解的也生效。对于返回全为json的数据的,可以直接使用@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    public GlobalControllerExceptionHandler() {

    }

    /**
     * 应用到所有@RequestMapping注解方法--调用控制器前先执行这个方法进行绑定验证器,就不用在每一个controller上进行设置了,最好在这里WebDataBinder绑定所有自定义的验证器
     * UserValidator这个验证器里面会设置这个验证器支持什么DTO对象进行验证
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        //绑定验证器
        binder.setValidator(new UserValidator());
    }

    /**
     * 初始化属性及其属性值--把值绑定到HttpServletRequest中,应用到所有@RequestMapping注解方法可以获取这个值
     *
     * @param request
     */
    @ModelAttribute
    public void addAttributes(HttpServletRequest request) {
        request.setAttribute("author", "Magical Sam");
    }

    /**
     * 对异常在controller上进行统一的处理,指定返回的json信息,也可以指定发生异常时返回的页面,就不需要加@ResponseBody注解了
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({FeignException.class})
    @ResponseBody
    public ResultBase timeoutException(FeignException e) {
        e.printStackTrace();
        ResultBase resultBase = new ResultBase();
        resultBase.setMsg("feign Exception:" + (StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : e.toString()));
        resultBase.setSuccess(false);
        return resultBase;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({RetryableException.class})
    @ResponseBody
    public ResultBase retryableException(RetryableException e) {
        e.printStackTrace();
        ResultBase resultBase = new ResultBase();
        resultBase.setMsg("Retryable Exception:" + (StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : e.toString()));
        resultBase.setSuccess(false);
        return resultBase;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({HystrixRuntimeException.class})
    @ResponseBody
    public ResultBase hystrixException(HystrixRuntimeException e) {
        e.printStackTrace();
        ResultBase resultBase = new ResultBase();
        resultBase.setMsg("rpcException:" + (StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : e.toString()));
        resultBase.setSuccess(false);
        return resultBase;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResultBase globalException(Exception e) {
        e.printStackTrace();
        ResultBase resultBase = new ResultBase();
        resultBase.setMsg(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : e.toString());
        resultBase.setSuccess(false);
        return resultBase;
    }
}
