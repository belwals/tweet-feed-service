package com.exp.service.tweet.feed.service.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
@Slf4j
@AllArgsConstructor
public class CustomKafkaProducer {

    private final KafkaProducer<String, String> kafkaProducer;

    public void publish(String destinationTopic, String key, String value) {
        ProducerRecord<String, String> record = new ProducerRecord<>(destinationTopic, key, value);

        kafkaProducer.send(record, ((recordMetadata, e) -> {
            if (isNull(e)) {
                log.info("Published message successfully of key={} to topic={}", record.key(), destinationTopic);
            } else {
                log.error("Error occurred while publishing message of key={} to topic={}", record.key(), destinationTopic, e);
            }
        }));
    }
}
