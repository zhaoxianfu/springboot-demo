package com.springboot.demo.enums;

import com.springboot.demo.enums.base.KeyValueExtEnum;

/**
 * @ClassName:OperationTypeEnum
 * @Despriction: 操作类型
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/17  12:59
 * @Version1.0
 **/

public enum OperationTypeEnum implements KeyValueExtEnum {

    /**
     * 操作类型
     */
    UNKNOWN("1", "unknown"),
    DELETE("2", "delete"),
    SELECT("3", "select"),
    UPDATE("4", "update"),
    INSERT("5", "insert");

    private String code;

    private String value;

    OperationTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getValue() {
        return value;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "OperationTypeEnum{" +
                "code='" + code + '\'' +
                ", value='" + value + '\'' +
                "} " + super.toString();
    }

    public static OperationTypeEnum getEnumByCode(String code) {
        for (OperationTypeEnum temp : values()) {
            if (code.equals(temp.getCode())) {
                return temp;
            }
        }
        return null;
    }
}
