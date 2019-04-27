package com.redis.demo.provider;

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
public class I18NController {

    @Autowired
    private MessageSource messageSource;

    @RequestMapping("/")
    public String i18n() {
        return "/login/login";
    }

}
