package com.rabbitmq.recesiver.fanout;

import com.rabbitmq.config.FanoutExchangeConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FanoutQueueListener {
 
    /**
     * fanout交换机： 扇型交换机，这个交换机没有路由键概念，就算你绑了路由键也是无视的。 这个交换机在接收到消息后，会直接转发到绑定到它上面的所有队列
     * 同一个队列监听多次，只会消费一次。
     * 交换机绑定的多个队列都可以收到消息
     * @param testMessage
     */
    @RabbitHandler
    @RabbitListener(queues = FanoutExchangeConfig.FANOUT_QUEUE)
    public void process(String testMessage) {
        System.out.println("FanoutReceiver消费者收到消息1  : " + testMessage);
    }

 
    @RabbitHandler
    @RabbitListener(queues = FanoutExchangeConfig.FANOUT_QUEUE2)
    public void process2(String testMessage) {
        System.out.println("FanoutReceiver消费者收到消息2  : " + testMessage);
    }
 
}