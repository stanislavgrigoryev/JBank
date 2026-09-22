package com.jbank.creditservice.kafka;

import com.jbank.creditservice.entity.CreditContract;
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
    public ProducerFactory<String, CreditContract> producerFactory() {

        Map<String, Object> configProperties = new HashMap<>();
        configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9094");
        configProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JacksonJsonDeserializer.class);

        return new DefaultKafkaProducerFactory<>(configProperties, new StringSerializer(), new JacksonJsonSerializer<>());
    }

    @Bean
    public KafkaTemplate<String, CreditContract> kafkaTemplate(ProducerFactory<String, CreditContract> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
