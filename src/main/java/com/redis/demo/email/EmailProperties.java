package com.redis.demo.email;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName:MailProperties
 * @Despriction:
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/11  17:35
 * @Version1.0
 **/
@Slf4j
@Component
@Data
public class EmailProperties {

    /**
     * 邮件发送者
     */
    @Value("${mail.fromMail.sender}")
    private String sender;

    /**
     * 邮件接收者
     */
    @Value("${mail.fromMail.receiver}")
    private String receiver;



}
