package com.redis.demo.enums;

/**
 * @ClassName:OperationUnitEnum
 * @Despriction: 被操作的单元
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/17  12:57
 * @Version1.0
 **/
public enum OperationUnitEnum {

    /**
     * 被操作的单元
     */
    UNKNOWN("1","unknown"),
    USER("2","user"),
    EMPLOYEE("3","employee"),
    Redis("4","redis");

    private String code;

    private String value;

    OperationUnitEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
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
    }}
