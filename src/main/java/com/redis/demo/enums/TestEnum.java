package com.redis.demo.enums;

import com.redis.demo.enums.base.KeyValueEnum;

/**
 * @ClassName:CoveragePeriodCategoryEnum
 * @Despriction: 测试枚举
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/17  21:56
 * @Version1.0
 **/
public enum TestEnum implements KeyValueEnum {

    /**
     * 测试
     */
    UNKNOWN(1, "unknown"),
    DELETE(2, "delete"),
    SELECT(3, "select"),
    UPDATE(4, "update"),
    INSERT(5, "insert");

    private Integer code;

    private String value;

    TestEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getValue() {
        return value;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "TestEnum{" +
                "code=" + code +
                ", value='" + value + '\'' +
                "} " + super.toString();
    }

}
