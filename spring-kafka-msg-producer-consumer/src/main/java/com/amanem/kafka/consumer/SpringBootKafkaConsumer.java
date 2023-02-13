package com.amanem.kafka.consumer;

import com.amanem.kafka.model.Tutorial;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SpringBootKafkaConsumer {

    @KafkaListener(topics = "${topic.name}", containerFactory = "kafkaListenerContainerFactory" ,groupId = "group_id_1")
    public void listen(Tutorial value){
        System.out.println("Message Received :: "+value.toString());
    }
}
