package com.rabbitmq.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/direct")
public class DirectController {
    @Resource
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendDirectMessage")
    public String sendMessage(){
        //这里对应着DirectExchangeConfig中的配置，直连时候只有交换机和routerkey都一样才可以
        rabbitTemplate.convertAndSend("directExchange","direct2","hello2");
        return "消息发送成功";
    }
}
