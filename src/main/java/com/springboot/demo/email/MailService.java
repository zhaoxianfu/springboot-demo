package com.springboot.demo.email;

/**
 * @ClassName:MailService
 * @Despriction:
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/14  21:08
 * @Version1.0
 **/
public interface MailService {

    void sendTextMail(MailDO mailDO);

    void sendHtmlMail(MailDO mailDO, boolean isShowHtml);

    void sendTemplateMail(MailDO mailDO);
}
