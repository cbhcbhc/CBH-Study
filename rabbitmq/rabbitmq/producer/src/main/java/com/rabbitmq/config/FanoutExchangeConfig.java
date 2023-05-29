package com.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutExchangeConfig {
 
    public static final String FANOUT_QUEUE = "fanoutQueue";
    public static final String FANOUT_QUEUE2 = "fanoutQueue2";
    public static final String FANOUT_QUEUE3 = "fanoutQueue3";
    public static final String FANOUT_EXCHANGE = "fanoutExchange";
    // fanout它不需要绑定routingkey
    public static final String FANOUT_ROUTING_KEY = "fanout";
 
    @Bean
    public Queue fanoutQueue() {
        return new Queue(FANOUT_QUEUE, true);
    }
 
    @Bean
    public Queue fanoutQueue2() {
        return new Queue(FANOUT_QUEUE2, true);
    }
 
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE, true, false);
    }
 
    @Bean
    public Binding bindingFanoutExchange(Queue fanoutQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder
                .bind(fanoutQueue)
                .to(fanoutExchange);
    }
 
    @Bean
    public Binding bindingFanoutExchange2(Queue fanoutQueue2, FanoutExchange fanoutExchange) {
        return BindingBuilder
                .bind(fanoutQueue2)
                .to(fanoutExchange);
    }
}