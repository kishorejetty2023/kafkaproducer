package com.example.kafka.producer.service;

import com.example.kafka.producer.dto.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class KafkaMessagePublisher {

    @Value("${app.kafka.topic-name}")
    private String topic;
    private final KafkaTemplate<String,Object> template;


    public KafkaMessagePublisher(KafkaTemplate<String,Object> template){
        this.template = template;
    }

    public void sendMessageToTopic(String message) {
        CompletableFuture<SendResult<String, Object>> future = template.send(topic, message);
        future.whenComplete(((result, ex) -> {

            if (ex == null) {
                log.info("sent Message=[ {} ] with offset=[ {} ]"
                        , message, result.getRecordMetadata().offset());

            } else {
                log.error("Unable to send the message=[ {} ] due to =[{}]"
                        , message, ex.getMessage());
            }
        }));
    }
        public void sendEventsToTopic(Customer customer){
            CompletableFuture<SendResult<String, Object>> fut = template.send(topic, customer);
            fut.whenComplete(((result, ex) -> {

                if(ex== null){
                    log.info("sent Message=[ {} ] with offset=[ {} ]"
                            ,customer.toString(),result.getRecordMetadata().offset());

                }
                else{
                    log.error("Unable to send the message=[ {} ] due to =[{}]"
                            ,customer.toString(),ex.getMessage());
                }
            }));
    }


}
