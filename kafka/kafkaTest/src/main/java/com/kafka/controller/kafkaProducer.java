package com.kafka.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/msg")
@Slf4j
public class kafkaProducer {
    private final static  String TOPIC_NAME = "my-topic";
    @Resource
    private KafkaTemplate<String,String> kafkaTemplate;

    @RequestMapping("/send")
    public String  sendMessage(){
        for (int i = 0; i < 600; i++) {
            kafkaTemplate.send(TOPIC_NAME, 0 ,"key", "this is" + (i+1) + "条 kafka message!");
            log.info("消息发送成功！");
        }

        return "消息发送成功";
    }
}
