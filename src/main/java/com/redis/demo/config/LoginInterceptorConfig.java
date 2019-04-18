package com.redis.demo.config;

import com.redis.demo.interceptor.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName:WebConfigurer
 * @Despriction: 配置静态资源和注册拦截器
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/18  11:15
 * @Version1.0
 **/

@Slf4j
@Configuration   //这个注解和@Component作用类似,
//只不过在@Component的作用上增加了可以进行扫描带有@Configuration类里面@bean注解,进行注入方法返回值对象到容器中
@ConditionalOnBean({LoginInterceptor.class})
public class LoginInterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    /**
     * 这个方法是用来配置静态资源的，比如html,js,css,等等
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    }

    /**
     * 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns("/**") 表示拦截所有的请求，
        // excludePathPatterns("/login", "/register") 表示除了登陆与注册之外，因为登陆注册的接口不需要登陆也可以访问
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/login", "/register");
    }

}
