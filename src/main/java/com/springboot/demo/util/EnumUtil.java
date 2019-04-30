package com.springboot.demo.util;


import com.springboot.demo.enums.TestEnum;
import com.springboot.demo.enums.base.KeyValueEnum;
import lombok.extern.slf4j.Slf4j;

/**
 * 类ZaEnumUtil.java的实现描述：枚举工具类
 *
 * @author zhaoxianfu 2019年4月17日
 */

@Slf4j
public class EnumUtil {

    /**
     * 根据枚举字节码对象和枚举的code-->获取这个枚举对象
     *
     * @param clazz
     * @param code
     * @param <T>
     * @return
     */
    public static <T extends Enum<T>> T getEnum(Class<T> clazz, Integer code) {
        for (T t : clazz.getEnumConstants()) {

            if (t instanceof KeyValueEnum) {
                if (code.equals(((KeyValueEnum) t).getCode())) {
                    return t;
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        TestEnum enum1 = EnumUtil.getEnum(TestEnum.class, 1);
        System.out.println(enum1);
        TestEnum enum2 = EnumUtil.getEnum(TestEnum.class, 2);
        System.out.println(enum2);
        TestEnum enum3 = EnumUtil.getEnum(TestEnum.class, 3);
        System.out.println(enum3);
    }
}
