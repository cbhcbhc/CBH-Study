package com.rabbitmq.recesiver.direct;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DirectQueueListener {
    @RabbitListener(queues = {"directQueue2"})
    public void process(String message){
        System.out.println("接收到的消息为："+ message);
    }
}
