package com.atguigu.boot3.message.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * @author 2407009
 * @Description
 * @create 2024-08-01
 */
@Configuration
public class AppKafkaConfiguration {
    @Bean
    public NewTopic topic() {
        return TopicBuilder.name("topic1")
                .partitions(1)
                .compact()
                .build();
    }
}
