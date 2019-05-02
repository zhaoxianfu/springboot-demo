package com.springboot.demo.config;

import com.springboot.demo.enums.base.KeyValueEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

/**
 * @ClassName:EnumCodeConverterEnumFactory
 * @Despriction: 枚举Code转换为枚举对象的工厂类--->自定义转换器,可以通过前端传入的枚举的code进行映射到枚举,传递给接口参数为一个枚举对象
 * 与我们通过@RequestBody里面通过json对象传递枚举属性为String类型的映射枚举的转换类是互不影响的,一直使用的是Spring MVC提供的默认的String转Enum的转换器,不会被我们提供的这个替换掉
 * --->这个针对Content-Type为x-www-form-urlencoded的传递类型的会把默认的String转Enum的默认转换器给替换掉
 * <p>
 * 前端传入的code不管是Integer还是String类型,前后端交互时都为String类型,只不过有Spring MVC设置的默认转换器,帮助我们把String类型转换为我们形式参数接收的类型,比如Long
 * 在我们设置转换器的时候,不管前段传入的是Integrt还是String类型,在Spring MVC和Spring Boot中,我们后端接收到都为String类型,从客户端接收到的请求都被视为String类型,所以只能用String类型的Code转枚举的converter。
 * @Author:zhaoxianfu
 * @Date:Created 2019/5/2  14:15
 * @Version1.0
 **/

@Slf4j
@Component
@ConditionalOnProperty(prefix = "converter.enumCodeConverter", name = "enabled", havingValue = "true", matchIfMissing = false)
public class EnumCodeConverterEnumFactory implements ConverterFactory<String, KeyValueEnum> {

    @Override
    public <T extends KeyValueEnum> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToEnum<>(targetType);
    }

    /**
     * 静态内部类
     *
     * @param <T>
     */
    @SuppressWarnings("all")
    private static class StringToEnum<T extends KeyValueEnum> implements Converter<String, T> {
        private Class<T> targerType;

        public StringToEnum(Class<T> targerType) {
            this.targerType = targerType;
        }

        @Override
        public T convert(String code) {
            if (StringUtils.isEmpty(code)) {
                return null;
            }
            return (T) EnumCodeConverterEnumFactory.getEnum(this.targerType, code);
        }
    }

    /**
     * 根据枚举里面的code获取枚举对象
     *
     * @param targerType
     * @param code
     * @param <T>
     * @return
     */
    public static <T extends KeyValueEnum> Object getEnum(Class<T> targerType, String code) {
        for (T enumObj : targerType.getEnumConstants()) {
            if (code.equals(String.valueOf(enumObj.getCode()))) {
                return enumObj;
            }
        }
        return null;
    }

}
