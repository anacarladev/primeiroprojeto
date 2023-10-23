package br.com.aulajavaanacarla.primeiroprojeto.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

    @KafkaListener(topics = "teste-rogerio", id = "GROUP_ID", groupId = "GROUP_ID", concurrency = "10")
    public void listen(String in,
                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(KafkaHeaders.OFFSET) long offset){
        LOGGER.info("Received: {} from {} @ {}", in, topic, offset);
    }
}
