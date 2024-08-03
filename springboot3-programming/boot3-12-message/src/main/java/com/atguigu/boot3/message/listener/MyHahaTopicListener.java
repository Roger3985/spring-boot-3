package com.atguigu.boot3.message.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

/**
 * @author 2407009
 * @Description
 * @create 2024-08-01
 */
@Component
public class MyHahaTopicListener {

    // 默認的監聽是從消息佇列的最後一個消息開始拿。新消息才能拿到。
    @KafkaListener(topics = "newshaha", groupId = "haha")
    public void haha(ConsumerRecord record) {
        // 1. 獲取主題的詳細訊息
        String topic = record.topic();
        System.out.println(topic);
        Object key = record.key();
        Object value = record.value();
        System.out.println("收到消息[key: " + key + "], [value: " + value + "]");
    }

    // 拿到以前的完整消息，
    @KafkaListener(groupId = "hehe", topicPartitions = {
            @TopicPartition(topic = "newshaha", partitionOffsets = {
                    @PartitionOffset(partition = "0", initialOffset = "0")
            })
    })
    public void hehe(ConsumerRecord record) {
        // 1. 獲取主題的詳細訊息
        String topic = record.topic();
        System.out.println(topic);
        Object key = record.key();
        Object value = record.value();
        System.out.println("============== 收到消息[key: " + key + "], [value: " + value + "] =================");
    }
}
