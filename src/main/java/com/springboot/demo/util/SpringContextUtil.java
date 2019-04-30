package com.springboot.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @ClassName:SpringContextUtil
 * @Despriction:  Spring的ApplicationContext的持有者,可以获取ApplicationContext,也可以用静态方法的方式获取spring容器中的bean\
 *
 * 根据实现ApplicationContextAware这个接口,通过里面的setApplicationContext就可以获取到当前服务中的applicationContext这个bean容器
 *
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/20  19:48
 * @Version1.0
 **/

@Slf4j
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * 重写方法,形式参数列表中会传入ApplicationContext对象,我们就可以通过传入的ApplicationContext获取到这个对象,进而辅值给这个工具类的applicationContext属性
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
        log.info("获取ApplicationContext对象:"+SpringContextUtil.applicationContext);
    }

    /**
     * 从工具类中获取ApplicationContext对象
     * 
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        assertApplicationContext();
        return applicationContext;
    }

    /**
     * 根据对象名称去容器中获取bean对象
     *
     * @param beanName
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        assertApplicationContext();
        return (T) applicationContext.getBean(beanName);
    }

    /**
     * 根据对象的类型去容器中获取bean对象
     *
     * @param requiredType
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> requiredType) {
        assertApplicationContext();
        return applicationContext.getBean(requiredType);
    }

    /**
     * 对容器是否为空进行判断,如果为空的话,就抛出异常信息
     */
    private static void assertApplicationContext() {
        if (SpringContextUtil.applicationContext == null) {
            throw new RuntimeException("applicaitonContext属性为null,请检查是否注入了SpringContextHolder!");
        }
    }

}
