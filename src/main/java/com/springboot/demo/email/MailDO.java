package com.springboot.demo.email;

import lombok.Data;

import java.util.Map;

/**
 * @ClassName:MailDO
 * @Despriction: 邮件接收参数
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/14  20:17
 * @Version1.0
 **/
@Data
public class MailDO {

    /**
     * 标题
     */
    private String title;

    /**
     *邮件内容
     */
    private String content;

    /**
     * 接收人邮件地址
     */
    private String email;

    /**
     * 附加数据 value 文件的绝对地址/动态模板数据
     */
    private Map<String, Object> attachment;

}
