package br.com.aulajavaanacarla.primeiroprojeto.kafka;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class Producer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendToTopic(String msg){
        LOGGER.info("vai publicar msg");
        kafkaTemplate.send("teste-rogerio",msg);
        LOGGER.info("publicou msg");
    }
}
