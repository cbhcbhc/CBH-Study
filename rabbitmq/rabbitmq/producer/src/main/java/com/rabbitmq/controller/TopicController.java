package com.rabbitmq.controller;

import com.rabbitmq.config.TopicExchangeConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/topic")
public class TopicController {

    @Resource
    private RabbitTemplate rabbitTemplate;

 
    @GetMapping("send")
    public Object sendMsg(String routingKey) {
        rabbitTemplate.convertAndSend(TopicExchangeConfig.TOPIC_EXCHANGE, routingKey, "发送一条测试消息：topic");
        return "topic消息发送成功！！";
    }
}