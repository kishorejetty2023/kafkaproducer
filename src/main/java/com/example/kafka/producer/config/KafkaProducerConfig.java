package com.example.kafka.producer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaProducerConfig {

    @Value("${app.kafka.topic-name}")
    private String topic;
    @Value("${app.kafka.partitions}")
    private int partitions;
    @Value("${app.kafka.replicationFactor}")
    private int replicationFactor;

    @Bean
    public NewTopic createTopic(){

        return new NewTopic(topic,partitions, (short) replicationFactor);
    }
}
