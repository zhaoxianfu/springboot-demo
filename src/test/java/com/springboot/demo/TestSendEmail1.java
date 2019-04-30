package com.springboot.demo;

import com.springboot.demo.email.EmailProperties;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @ClassName:TestSendEmail
 * @Despriction: 测试发送邮件
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/11  16:15
 * @Version1.0
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootDemoApplication.class)
public class TestSendEmail1 {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmailProperties emailProperties;

    /**
     * 发送文本邮件
     */
    @Test
    public void sendMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailProperties.getSender());
        message.setTo(emailProperties.getReceiver());
        message.setSubject("赵宪福发送文本邮件测试");   // 标题
        message.setText("世界，你好！--->文本邮件");  // 内容
        try {
            javaMailSender.send(message);
            log.info("文本邮件发送成功！");
        } catch (Exception e) {
            log.error("文本邮件发送异常！", e);
        }
    }

    /**
     * 发送HTML邮件
     */
    @Test
    public void sendMail1() {
        String content = "<html><body><h3>hello world ! --->Html邮件</h3></body></html>";

        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(emailProperties.getSender());
            helper.setTo(emailProperties.getReceiver());
            helper.setSubject("赵宪福发送Html邮件测试");
            helper.setText(content, true);

            javaMailSender.send(message);
            log.info("Html邮件发送成功！");

        } catch (MessagingException e) {
            log.error("Html邮件发送异常！", e);
        }
    }

    /**
     * 发送附件邮件
     */
    @Test
    public void sendMail2() {
        String filePath1 = "D:\\123.pdf";

        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(emailProperties.getSender());
            helper.setTo(emailProperties.getReceiver());
            helper.setSubject("赵宪福发送附件邮件测试");
            helper.setText("一封带附件的邮件", true);

            //上传的附件1
            FileSystemResource file1 = new FileSystemResource(new File(filePath1));
            String fileName1 = filePath1.substring(filePath1.lastIndexOf(File.separator));
            log.info("上传附件1的附件名称为{}", fileName1);

            helper.addAttachment(fileName1, file1);

            javaMailSender.send(message);
            log.info("附件邮件发送成功！");
        } catch (MessagingException e) {
            log.error("附件邮件发送异常！", e);
        }
    }

    /**
     * 发送图片邮件
     */
    @Test
    public void sendMail3() {
        String id = "xieke90";
        // cid:内嵌资源
        String content = "<html><body>带有图片的邮件：<img src=\'cid:" + id + "\'></body></html>";
        String imgPath = "F:\\2222.jpg";
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(emailProperties.getSender());
            helper.setTo(emailProperties.getReceiver());
            helper.setSubject("赵宪福发送图片邮件测试");
            helper.setText(content, true);

            FileSystemResource res = new FileSystemResource(new File(imgPath));
            helper.addInline(id, res);

            javaMailSender.send(message);
            log.info("图片邮件发送成功！");
        } catch (MessagingException e) {
            log.error("图片邮件发送异常！", e);
        }
    }
}
