package com.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class kafkaConsumer {

    @KafkaListener(topics = "my-topic", groupId = "MyGroup1")
    public void listenGroup(ConsumerRecord<String, String> record, Acknowledgment ack){
        String value = record.value();
        log.info("消息的值为 {} ", value );
        log.info("record的值为 {} ", record );

        //⼿动提交offset
        ack.acknowledge();

    }

//    @KafkaListener(groupId = "testGroup", topicPartitions = {
//            @TopicPartition(topic = "topic1", partitions = {"0", "1"}), // 指定消费topic1下的0，1分区
//            // 指定消费topic2下的0分区，从offset+1开始消费；消费topic2下的1分区，指定从offset为100的位置开始消费
//            @TopicPartition(topic = "topic2", partitions = "0", partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "100"))
//    })
//    public void listenGroup2(ConsumerRecord<String, String> record, Acknowledgment ack){
//        String value = record.value();
//        System.out.println(value);
//        System.out.println(record);
//        ack.acknowledge(); // 手动提交offset（如果没有设置手动提交offset，消息会被重复消费）
//    }



}
