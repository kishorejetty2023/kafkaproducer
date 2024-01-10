package com.example.kafka.producer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessagePublisher {

    @Value("${app.kafka.topic-name}")
    private String topic;
    private final KafkaTemplate<String,Object> template;


    public KafkaMessagePublisher(KafkaTemplate<String,Object> template){
        this.template = template;
    }

    public void sendMessageToTopic(String message){
        CompletableFuture<SendResult<String, Object>> future = template.send(topic, message);
        future.whenComplete(((result, ex) -> {

            if(ex== null){
                System.out.println("sent Message=["+message+
                        "] with offset=["+result.getRecordMetadata().offset()+"]");

            }
            else{
                System.out.println("Unable to send the message=["+message+
                        "] due to =["+ ex.getMessage()+"]");
            }
        }));
    }


}
