package com.amanem.kafka.producer;

import com.amanem.kafka.model.Tutorial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.kafka.clients.producer.ProducerRecord;

@Service
public class SpringBootKafkaProducer {
    private static final Logger logger =
            LoggerFactory.getLogger(SpringBootKafkaProducer.class);

    @Autowired
    public KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaTemplate<String, Tutorial> tutorialKafkaTemplate;


    public void sendMessage(String value) {
        ListenableFuture<SendResult<String,String>> future = kafkaTemplate.send("spring_boot_kafka_topic_v3", value);

        future.addCallback(new ListenableFutureCallback() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Messages failed to push on topic");
            }

            @Override
            public void onSuccess(Object result) {
                System.out.println("Messages successfully pushed on topic");
            }
        });
    }

    public void sendMessage(Tutorial message) {
        ProducerRecord<String,Tutorial> producerRecord =new ProducerRecord<>("spring_boot_kafka_topic_v1",message.getPublisher(),message);
        producerRecord.headers().add("client_id","123".getBytes());
        ListenableFuture<SendResult<String,Tutorial>> future = tutorialKafkaTemplate.send(producerRecord);
    //    ListenableFuture<SendResult<String,Tutorial>> future = tutorialKafkaTemplate.send("spring_boot_kafka_topic_v1", message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, Tutorial>>() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Messages failed to push on topic");
            }

            @Override
            public void onSuccess(SendResult<String, Tutorial> result) {
                System.out.println("Messages successfully pushed on topic");
                logger.info("Sent message: " + message
                        + " with offset: " + result.getRecordMetadata().offset() + " to partition " + result.getRecordMetadata().partition());

            }
        });
    }



}
