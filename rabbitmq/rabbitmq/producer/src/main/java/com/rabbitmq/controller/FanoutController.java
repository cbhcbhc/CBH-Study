package com.rabbitmq.controller;

import com.rabbitmq.config.FanoutExchangeConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/fanout")
public class FanoutController {

    @Resource
    private  RabbitTemplate rabbitTemplate;

 
    /**
     * fanout交换机为扇形模式交换机
     *  消息会发送到所有绑定的队列上。
     * @return
     */
    @GetMapping("send")
    public String sendMsg() {
        rabbitTemplate.convertAndSend(FanoutExchangeConfig.FANOUT_EXCHANGE, null, "发送一条测试消息：fanout");
        return "fanout消息发送成功！！";
    }
}