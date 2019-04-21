package com.redis.demo.kafak;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @ClassName:KafkaListener
 * @Despriction: kafka监听的消费者
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/19  17:30
 * @Version1.0
 **/

@Slf4j
@Component
public class KafkaMessageListener {

    //id为消费者组id
   /* @KafkaListener(topics = {"test"})
    public void listen(ConsumerRecord<?, ?> record) {
        log.info("kafka的key: " + record.key());
        log.info("++++++++++++++++++++++++++++++++++++++");
        log.info("kafka的value: " + record.value().toString());
    }*/
}
