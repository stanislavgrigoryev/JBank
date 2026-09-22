package com.jbank.cardservice.kafka;

import com.jbank.cardservice.entity.Card;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public ProducerFactory<String, Card> producerFactory(){

        Map<String, Object> configProperties = new HashMap<>();
        configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9094");
        configProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JacksonJsonDeserializer.class);
        return new DefaultKafkaProducerFactory<>(configProperties, new StringSerializer(), new JacksonJsonSerializer<>());
    }

    @Bean
    public KafkaTemplate<String, Card> kafkaTemplate(ProducerFactory<String, Card> producerFactor){
        return new KafkaTemplate<>(producerFactor);
    }
}
