package com.rabbitmq.recesiver.topic;

import com.rabbitmq.config.TopicExchangeConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @className: TopicQueueListener
 * @description: 主题交换机的监听器

 */
@Slf4j
@Component
public class TopicQueueListener {
 
    /**
     * topic: 主题交换机
     * @param testMessage
     */
    @RabbitHandler
    @RabbitListener(queues = TopicExchangeConfig.TOPIC_QUEUE)
    public void process(String testMessage) {
        System.out.println("TopicReceiver消费者收到消息1  : " + testMessage);
    }

    /*消费者1.2会轮流消费*/
    @RabbitHandler
    @RabbitListener(queues = TopicExchangeConfig.TOPIC_QUEUE)
    public void process2(String testMessage) {
        System.out.println("TopicReceiver消费者收到消息2  : " + testMessage);
    }
 
    @RabbitHandler
    @RabbitListener(queues = TopicExchangeConfig.TOPIC_QUEUE2)
    public void process3(String testMessage) {
        System.out.println("TopicReceiver消费者收到消息3  : " + testMessage);
    }

    /*匹配所有*/
    @RabbitHandler
    @RabbitListener(queues = TopicExchangeConfig.TOPIC_QUEUE3)
    public void process4(String testMessage) {
        System.out.println("TopicReceiver消费者收到消息4  : " + testMessage);
    }
 
}