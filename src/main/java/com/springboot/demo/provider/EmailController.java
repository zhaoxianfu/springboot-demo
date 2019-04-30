package com.springboot.demo.provider;

import com.springboot.demo.email.MailDO;
import com.springboot.demo.email.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName:EmailController
 * @Despriction: 邮件发送接口
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/14  21:34
 * @Version1.0
 **/

@Slf4j
@RestController
public class EmailController {

    @Autowired
    private MailService mailService;

    /**
     * 发送文本邮件接口
     *
     * @param mailDO
     * @return
     */
    @GetMapping("testMail")
    public ResponseEntity<Void> testMail(MailDO mailDO) {
        try {
            mailService.sendTextMail(mailDO);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONTINUE);
        }
        return ResponseEntity.ok().build();
    }

    /**
     * 发送HTML邮件接口
     *
     * @param mailDO
     * @return
     */
    @GetMapping("htmlMail")
    public ResponseEntity<Void> htmlMail(MailDO mailDO) {
        try {
            Map<String, Object> map = new HashMap<>();

            //附件文档
            map.put("学习文件1.pdf", "D:\\123.pdf");
            map.put("学习文件2.pdf", "F:\\2435.pdf");
            map.put("科比.jpg", "F:\\2222.jpg");

            mailDO.setAttachment(map);
            mailService.sendHtmlMail(mailDO, false);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONTINUE);
        }
        return ResponseEntity.ok().build();
    }

    /**
     * 发送模板邮件接口
     *
     * @param mailDO
     * @return
     */
    @GetMapping("templateMail")
    public ResponseEntity<Void> templateMail(MailDO mailDO) {
        try {
            Map<String, Object> map = new HashMap<>();
            //模板中的变量属性
            map.put("username", "我就我，不一样的烟火");

            //附件文档
            map.put("学习文件1.pdf", "D:\\123.pdf");
            map.put("学习文件2.pdf", "F:\\2435.pdf");
            map.put("科比.jpg", "F:\\2222.jpg");

            mailDO.setAttachment(map);
            mailService.sendTemplateMail(mailDO);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONTINUE);
        }
        return ResponseEntity.ok().build();
    }
}
