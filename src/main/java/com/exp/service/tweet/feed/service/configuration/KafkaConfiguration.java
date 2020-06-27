package com.exp.service.tweet.feed.service.configuration;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.Properties;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;
import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.*;

@Configuration
public class KafkaConfiguration {

    private final static String CONSUMER_GROUP_ID = "KAFKA_PIPELINE_APP";

    @Bean
    public KafkaConsumer<String, String> getKafkaConsumer(@Value("${tweets.topic}") String topic, @Value("${bootstrap.server}") String bootstrapServer) {
        Properties props = buildKafkaConsumerProperties(bootstrapServer);
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(props);
        kafkaConsumer.subscribe(Collections.singletonList(topic));

        return kafkaConsumer;
    }

    @Bean
    public KafkaProducer<String, String> getKafkaProducer(@Value("${bootstrap.server}") String bootStrapServer) {
        Properties props = buildProducerProperties(bootStrapServer);
        return new KafkaProducer<>(props);

    }

    private Properties buildProducerProperties(String bootStrapServer) {
        Properties props = new Properties();

        props.setProperty(BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
        props.setProperty(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        props.setProperty(ENABLE_IDEMPOTENCE_CONFIG, "true");
        props.setProperty(ACKS_CONFIG, "all");
        props.setProperty(MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "5");
        props.setProperty(RETRIES_CONFIG, Integer.toString(Integer.MAX_VALUE));

        props.setProperty(COMPRESSION_TYPE_CONFIG, "snappy");
        props.setProperty(LINGER_MS_CONFIG, "20");
        props.setProperty(BATCH_SIZE_CONFIG, Integer.toString(32 * 1024));

        return props;
    }

    private Properties buildKafkaConsumerProperties(String bootstrapServer) {
        Properties prop = new Properties();

        prop.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        prop.setProperty(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        prop.setProperty(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        prop.setProperty(GROUP_ID_CONFIG, CONSUMER_GROUP_ID);
        prop.setProperty(AUTO_OFFSET_RESET_CONFIG, "earliest");

        return prop;
    }
}
