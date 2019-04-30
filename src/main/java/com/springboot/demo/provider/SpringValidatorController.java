package com.springboot.demo.provider;

import com.springboot.demo.pojo.User;
import com.springboot.demo.validator.UserValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:SpringValidatorController
 * @Despriction:
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/26  14:04
 * @Version1.0
 **/

@RestController
@RequestMapping("user")
public class SpringValidatorController {

    /**
     * 调用控制器前先执行这个方法进行绑定验证器
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        //绑定验证器
        binder.setValidator(new UserValidator());
    }

    /**
     * @Valid标注User,就会去SpringMVC中去遍历找对应的验证器,找到UserValidator执行里面的supports方法去验证User里面的数据
     *
     * @param user
     * @param Errors 验证器返回的错误
     * @return
     */
    @GetMapping("validatorUser")
    public Map<String, Object> validator(@Valid User user, Errors Errors) {

        Map<String, Object> map = new HashMap<>();
        map.put("user", user);

        //判断是否存在错误
        if (Errors.hasErrors()) {
            //获取全部错误
            List<ObjectError> allErrors = Errors.getAllErrors();
            for (ObjectError allError : allErrors) {
                //判断是否字段的值存在错误
                if (allError instanceof FieldError) {
                    //字段属性值错误,不符合
                    FieldError fe = (FieldError) allError;
                    map.put(fe.getField(), fe.getDefaultMessage());
                } else {
                    //对象错误
                    map.put(allError.getObjectName(), allError.getDefaultMessage());
                }
            }
        }
        return map;
    }

}
