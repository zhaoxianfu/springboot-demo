package com.redis.demo.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @ClassName:MailServiceImpl
 * @Despriction: 发送邮件
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/14  21:10
 * @Version1.0
 **/

@Service
@Slf4j
public class MailServiceImpl implements MailService {

    /**
     *thymeleaf模板的template模板引擎
     */
    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmailProperties emailProperties;

    /**
     * 纯文本邮件
     *
     * @param mailDO
     */
    @Override
    public void sendTextMail(MailDO mailDO) {

        //建立邮件消息
        SimpleMailMessage message = new SimpleMailMessage();
        // 发送人的邮箱
        message.setFrom(emailProperties.getSender());
        //标题
        message.setSubject(mailDO.getTitle());
        //发给谁  对方邮箱
        message.setTo(mailDO.getEmail());
        //邮件内容
        message.setText(mailDO.getContent());
        try {
            //发送邮件
            javaMailSender.send(message);
            log.info("发送纯文本邮件成功");
        } catch (MailException e) {
            log.error("纯文本邮件发送失败->message:{}", e.getMessage());
            throw new RuntimeException("纯邮件发送失败");
        }
    }

    /**
     * 发送的邮件是富文本（附件,图片,html等）
     *
     * @param mailDO
     * @param isShowHtml
     */
    @Override
    public void sendHtmlMail(MailDO mailDO, boolean isShowHtml) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            //是否发送的邮件是富文本（附件，图片，html等）
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            // 发送人的邮箱
            messageHelper.setFrom(emailProperties.getSender());
            //发给谁  对方邮箱
            messageHelper.setTo(mailDO.getEmail());
            //标题
            messageHelper.setSubject(mailDO.getTitle());
            //false，显示原始html代码，无效果
            messageHelper.setText(mailDO.getContent(), isShowHtml);
            //判断是否有附加图片,文件等
            if (mailDO.getAttachment() != null && mailDO.getAttachment().size() > 0) {
                mailDO.getAttachment().forEach((key, value) -> {
                    try {
                        File file = new File(String.valueOf(value));
                        if (file.exists()) {
                            messageHelper.addAttachment(key, new FileSystemResource(file));
                        }
                    } catch (MessagingException e) {
                        log.error("附件发送失败->message:{}", e.getMessage());
                        throw new RuntimeException("附件发送失败");
                    }
                });
            }
            //发送邮件
            javaMailSender.send(mimeMessage);
            log.info("发送富文本邮件成功");
        } catch (MessagingException e) {
            log.error("富文本邮件发送失败->message:{}", e.getMessage());
            throw new RuntimeException("富文本邮件发送失败");
        }
    }

    /**
     * 发送thymeleaf模板邮件 使用thymeleaf模板
     * <p>
     * 若果使用freemarker模板
     * Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
     * configuration.setClassForTemplateLoading(this.getClass(), "/templates");
     * String emailContent = FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate("mail.ftl"), params);
     *
     * @param mailDO
     */
    @Override
    public void sendTemplateMail(MailDO mailDO) {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            // 发送人的邮箱
            messageHelper.setFrom(emailProperties.getSender());
            //发给谁  对方邮箱
            messageHelper.setTo(mailDO.getEmail());
            //标题
            messageHelper.setSubject(mailDO.getTitle());
            //使用模板thymeleaf
            //Context是导这个包import org.thymeleaf.context.Context;
            Context context = new Context();
            //定义模板数据
            context.setVariables(mailDO.getAttachment());
            //获取thymeleaf的html模板
            //指定模板路径
            String emailContent = templateEngine.process("/mail/mail1", context);
            messageHelper.setText(emailContent, true);

            //判断是否有附加图片,文件等
            if (mailDO.getAttachment() != null && mailDO.getAttachment().size() > 0) {
                mailDO.getAttachment().forEach((key, value) -> {
                    try {
                        File file = new File(String.valueOf(value));
                        if (file.exists()) {
                            messageHelper.addAttachment(key, new FileSystemResource(file));
                        }
                    } catch (MessagingException e) {
                        log.error("附件发送失败->message:{}", e.getMessage());
                        throw new RuntimeException("附件发送失败");
                    }
                });
            }

            //发送邮件
            javaMailSender.send(mimeMessage);
            log.info("发送thymeleaf模板邮件成功");
        } catch (MessagingException e) {
            log.error("thymeleaf模板邮件发送失败->message:{}", e.getMessage());
            throw new RuntimeException("thymeleaf模板邮件发送失败");
        }
    }

}
