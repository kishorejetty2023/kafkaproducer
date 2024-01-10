package com.example.kafka.producer.controller;

import com.example.kafka.producer.service.KafkaMessagePublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/v1/producer")
public class KafkaMessageProducerController {

    private final KafkaMessagePublisher kafkaMessagePublisher;

    public KafkaMessageProducerController(KafkaMessagePublisher kafkaMessagePublisher){
        this.kafkaMessagePublisher =kafkaMessagePublisher;
    }

    @GetMapping("/publish/{message}")
    public ResponseEntity<String> sendMessage(@PathVariable String message){
        try {

            IntStream.range(1, 10000).forEach(
                    val -> {kafkaMessagePublisher.sendMessageToTopic(message+" : "+val);}
            );
            //kafkaMessagePublisher.sendMessageToTopic(message);
            return ResponseEntity.ok("Published Message successfully");
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();

        }
    }

}
