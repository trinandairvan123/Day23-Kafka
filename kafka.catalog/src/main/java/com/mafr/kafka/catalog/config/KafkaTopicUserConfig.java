package com.mafr.kafka.catalog.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicUserConfig {

    @Value("${spring.kafka.topic.order}")
    private String topicName;

    // spring bean for kafka topic
    @Bean
    public NewTopic topic(){
        System.out.println("topicName: "+topicName);
        return TopicBuilder.name(topicName)
                .partitions(1)
                .replicas(1)
                .compact()
                .build();
    }
}