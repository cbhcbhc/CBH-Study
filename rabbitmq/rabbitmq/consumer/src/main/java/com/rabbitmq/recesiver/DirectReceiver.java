package com.rabbitmq.recesiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DirectReceiver {
    @RabbitListener(queues = {"directQueue2"})
    public void process(String message){
        System.out.println("接收到的消息为："+ message);
    }
}
