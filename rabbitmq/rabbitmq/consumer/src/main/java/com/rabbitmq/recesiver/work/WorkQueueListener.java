package com.rabbitmq.recesiver.work;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class WorkQueueListener {
    @RabbitListener(queues = "WorkQueues", containerFactory = "listenerContainer")
    public void receiver(String data){   //这里直接接收String类型的数据
        System.out.println("一号消息队列监听器 "+data);
    }

    @RabbitListener(queues = "WorkQueues", containerFactory = "listenerContainer")
    public void receiver2(String data){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("二号消息队列监听器 "+data);
    }
}