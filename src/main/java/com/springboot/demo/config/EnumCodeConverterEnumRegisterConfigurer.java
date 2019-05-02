package com.springboot.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName:EnumCodeConverterEnumRegisterConfigurer
 * @Despriction: 注册枚举工厂转换类, 进而通过前端传入code就可以进行后端映射到这个枚举对象
 * @Author:zhaoxianfu
 * @Date:Created 2019/5/2  15:37
 * @Version1.0
 **/

@Slf4j
@Configuration
@ConditionalOnBean({EnumCodeConverterEnumFactory.class})
public class EnumCodeConverterEnumRegisterConfigurer implements WebMvcConfigurer {

    @Autowired
    private EnumCodeConverterEnumFactory enumCodeConverterEnumFactory;

    /**
     * 注册枚举工厂转换类,进而通过前端传入code就可以进行后端映射到这个枚举对象
     *
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(enumCodeConverterEnumFactory);
        log.info("--->Spring MVC中注册枚举code转枚举对象的枚举工厂转换类成功");
    }

}
