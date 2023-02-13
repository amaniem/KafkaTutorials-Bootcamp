package com.amanem.kafka.controller;

import com.amanem.kafka.model.Tutorial;
import com.amanem.kafka.producer.SpringBootKafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SpringBootKafkaRestController {

    @Autowired
    SpringBootKafkaProducer springBootKafkaProducer;

    @GetMapping(value = "/send/{message}")
    public void send(@PathVariable String message) {
        springBootKafkaProducer.sendMessage(message);
    }

    @PostMapping(value = "/sendTutorialMessage")
    public void send(@RequestBody Tutorial tutorial) {

        springBootKafkaProducer.sendMessage(tutorial);
    }

}
