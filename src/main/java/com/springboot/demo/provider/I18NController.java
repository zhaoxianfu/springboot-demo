package com.springboot.demo.provider;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName:I18NController
 * @Despriction:
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/27  19:36
 * @Version1.0
 **/

@Slf4j
@Controller("i18n")
@Api(tags = "国际化接口", description = "国际化接口")
public class I18NController {

    @Autowired
    private MessageSource messageSource;

    @RequestMapping("/")
    @ApiOperation(value = "国际化跳转页面接口", notes = "国际化跳转页面接口")
    public String i18n() {
        return "/login/login";
    }

}
