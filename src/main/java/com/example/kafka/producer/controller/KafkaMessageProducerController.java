package com.example.kafka.producer.controller;

import com.example.kafka.producer.dto.Customer;
import com.example.kafka.producer.service.KafkaMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.IntStream;

@Slf4j
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

    @PostMapping("/sendEvents")
    public ResponseEntity<String> sendEvents(@RequestBody Customer customer){

        try {
            kafkaMessagePublisher.sendEventsToTopic(customer);
            return ResponseEntity.ok("Published Event successfully");
        }
        catch (Exception ex){

            log.error("unable to send Message : {} with Exception {} ",customer.toString(),ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

}
