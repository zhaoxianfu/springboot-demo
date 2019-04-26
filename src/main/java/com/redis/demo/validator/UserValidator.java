package com.redis.demo.validator;

import com.redis.demo.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @ClassName:UserValidator
 * @Despriction: spring的验证器, JSR303只能解决简单的属性的校验
 *               有了这个验证器还不会启用,需要绑定给WebDataBinder机制,需要使用@InitBinder在执行控制器方法前,处理器会先执行标有@InitBinder标注的方法
 *               这样就可以将WebDataBinder对象作为参数传递到这个绑定验证器的方法中,通过这层关系WebDataBiner对象,这个对象有一个setValidator方法,
 *               他可以绑定自定义的验证器,这样就可以在获取参数之后,通过自定义的验证器去验证参数,webDataBinder的另外一个作用是可以进行参数的自定义
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/26  13:39
 * @Version1.0
 **/
public class UserValidator implements Validator {

    /**
     * 设置这个验证器支持什么DTO对象进行验证
     *
     * @param clazz
     * @return
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(User.class);
    }

    /**
     * 验证逻辑----可以对里面的参数进行计算判断是否符合规则
     *
     * @param target 为验证传入的DTO对象
     * @param errors 验证时出现的错误,错误收集对象
     */
    @Override
    public void validate(Object target, Errors errors) {

        if (null == target) {
            //直接在入参时报错,这样就不能进入控制器controller了
            errors.rejectValue("", "400", "用户不能为空");
            return;
        }
        //强制转换
        User user = (User) target;
        //UserName非空字符串
        if (StringUtils.isBlank(user.getUserName())) {
            //增加错误
            errors.rejectValue("UserName", null, "UserName不能为空");
        }
    }
}
