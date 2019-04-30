package com.springboot.demo.provider;

import com.springboot.demo.dto.ValidatorDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:ValidController
 * @Despriction: JSR303校验属性的controller
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/26  11:49
 * @Version1.0
 **/

@RestController
public class ValidController {

    /**
     * @param validatorDTO
     * @param errors       验证时出现的错误,错误收集对象
     * @return
     */
    @RequestMapping("valid/validate")     //会将JSR303的验证结果放到Errors对象中
    public Map<String, Object> validate(@Valid @RequestBody ValidatorDTO validatorDTO, Errors errors) {

        HashMap<String, Object> errMap = new HashMap<>();
        //获取错误列表
        List<ObjectError> allErrors = errors.getAllErrors();

        for (ObjectError allError : allErrors) {
            String key = null;
            String msg = null;
            //字段错误,因为里面属性值的问题
            if (allError instanceof FieldError) {
                FieldError fe = (FieldError) allError;
                //获取错误验证字段名
                key = fe.getField();
            } else {
                //非字段错误,其他的一些问题
                allError.getObjectName();   //获取验证对象名称
            }
            //错误信息
            msg = allError.getDefaultMessage();
            errMap.put(key, msg);
        }
        return errMap;
    }
}
