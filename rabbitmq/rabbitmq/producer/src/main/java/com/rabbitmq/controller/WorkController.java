package com.rabbitmq.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/work")
public class WorkController {
    @Resource
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/send")
    public String sendMessage(){
        for (int i = 0; i < 20; i++) {
            rabbitTemplate.convertAndSend("WorkQueues","work message"+ i);

        }
        return "消息发送成功";
    }
}
