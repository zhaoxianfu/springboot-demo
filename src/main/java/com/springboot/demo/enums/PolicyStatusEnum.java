package com.springboot.demo.enums;

import com.springboot.demo.enums.base.KeyValueEnum;

/**
 * @ClassName:PolicyStatusEnum
 * @Despriction: 保单状态枚举对象
 * @Author:zhaoxianfu
 * @Date:Created 2019/5/2  14:53
 * @Version1.0
 **/
public enum PolicyStatusEnum implements KeyValueEnum {

    POLICY_EFFECT(1, "生效"),
    TERMINATION(2, "保单终止"),
    EXPIRY(3, "失效");

    private Integer code;

    private String value;

    private PolicyStatusEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public static PolicyStatusEnum getEnum(Integer code) {
        for (PolicyStatusEnum an : values()) {
            if (an.getCode().equals(code)) {
                return an;
            }
        }
        return PolicyStatusEnum.POLICY_EFFECT;
    }

    public static PolicyStatusEnum getEnum(String value) {
        for (PolicyStatusEnum an : values()) {
            if (an.getValue().equals(value)) {
                return an;
            }
        }
        return PolicyStatusEnum.POLICY_EFFECT;
    }

    public static String getValue(Integer code) {
        if (null == code) {
            return null;
        }
        PolicyStatusEnum em = getEnum(code);
        return em.getValue();
    }

    public static Integer getCode(String value) {
        if (null == value) {
            return null;
        }
        PolicyStatusEnum em = getEnum(value);
        return em.getCode();
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "PolicyStatusEnum{" +
                "code=" + code +
                ", value='" + value + '\'' +
                "} " + super.toString();
    }
}
