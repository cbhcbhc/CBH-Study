package com.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @className: TopicExchangeConfig
 * @description:
 * *  (星号) 用来表示一个单词 (必须出现的)
 * #  (井号) 用来表示任意数量（零个或多个）单词
 */
@Configuration
public class TopicExchangeConfig {
 
    public static final String TOPIC_QUEUE = "topicQueue";
    public static final String TOPIC_QUEUE2 = "topicQueue2";
    public static final String TOPIC_QUEUE3 = "topicQueue3";
    public static final String TOPIC_EXCHANGE = "topicExchange";
    public static final String TOPIC_ROUTING_KEY = "topic*";
 
    @Bean
    public Queue topicQueue() {
        return new Queue(TOPIC_QUEUE, true);
    }
 
    @Bean
    public Queue topicQueue2() {
        return new Queue(TOPIC_QUEUE2, true);
    }
 
    @Bean
    public Queue topicQueue3() {
        return new Queue(TOPIC_QUEUE3, true);
    }
 
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE, true, false);
    }
 
    @Bean
    public Binding bindingTopicExchange(Queue topicQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(topicQueue).to(topicExchange).with("topic.#");
    }
 
    @Bean
    public Binding bindingTopicExchange2(Queue topicQueue2, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(topicQueue2)
                .to(topicExchange)
                .with("test.#");
    }
 
    @Bean
    public Binding bindingTopicExchange3(Queue topicQueue3, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(topicQueue3)
                .to(topicExchange)
                .with("#");
    }
}