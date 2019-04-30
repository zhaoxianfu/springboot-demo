package com.springboot.demo.model;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName:OperationLog
 * @Despriction:  日志记录对象
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/17  13:05
 * @Version1.0
 **/

@Data
public class OperationLog {

    /**
     * 主键id
     */
    private String id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 日志等级
     */
    private Integer level;

    /**
     * 被操作的对象
     */
    private String operationUnit;

    /**
     * 方法名
     */
    private String method;

    /**
     * 参数
     */
    private String args;

    /**
     * 操作人id
     */
    private String userId;

    /**
     * 操作人
     */
    private String userName;

    /**
     * 日志描述
     */
    private String describe;

    /**
     * 操作类型
     */
    private String operationType;

    /**
     * 方法运行时间
     */
    private Long runTime;

    /**
     * 方法返回值
     */
    private String returnValue;

}
