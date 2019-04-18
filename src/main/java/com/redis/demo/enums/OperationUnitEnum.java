package com.redis.demo.enums;

import com.redis.demo.enums.base.KeyValueExtEnum;

/**
 * @ClassName:OperationUnitEnum
 * @Despriction: 被操作的单元
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/17  12:57
 * @Version1.0
 **/
public enum OperationUnitEnum implements KeyValueExtEnum {

    /**
     * 被操作的单元
     */
    UNKNOWN("1", "unknown"),
    USER("2", "user"),
    EMPLOYEE("3", "employee"),
    Redis("4", "redis");

    private String code;

    private String value;

    OperationUnitEnum(String code, String value) {
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
        return "OperationUnitEnum{" +
                "value='" + value + '\'' +
                ", code='" + code + '\'' +
                ", value='" + value + '\'' +
                "} " + super.toString();
    }

}
