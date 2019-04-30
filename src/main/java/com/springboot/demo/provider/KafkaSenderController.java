package com.springboot.demo.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName:KafkaSenderController
 * @Despriction:
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/19  17:27
 * @Version1.0
 **/

@Slf4j
@RestController
@RequestMapping("kafka")
public class KafkaSenderController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping(value = "/send")
    public String sendKafka(String message) {
        try {
            log.info("kafka的消息={}", message);
            //test作为topic,key配置为key,message再页面进行发送，进行测试

            kafkaTemplate.send("test", "key", message);
            log.info("发送kafka成功.");
            return "发送kafka成功";
        } catch (Exception e) {
            log.error("发送kafka失败", e);
            return "发送kafka失败";
        }
    }

}
