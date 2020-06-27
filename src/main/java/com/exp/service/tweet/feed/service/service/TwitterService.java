package com.exp.service.tweet.feed.service.service;

import com.twitter.hbc.core.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;

@Slf4j
@Service
public class TwitterService {

    private final Client client;
    private final CustomKafkaProducer publisher;
    private final String topic;
    private final BlockingQueue<String> blockingQueue;

    public TwitterService(Client client, CustomKafkaProducer publisher, @Value("${tweets.topic}") String topic,
                          @Qualifier("tweetQueue") BlockingQueue<String> blockingQueue) {
        this.client = client;
        this.publisher = publisher;
        this.topic = topic;
        this.blockingQueue = blockingQueue;
    }

    public void getActiveConnectionAndPublish() {

        log.info("Connecting Streams...");
        client.connect();
        while (!client.isDone()) {
            String message = null;

            try {
                message = blockingQueue.take();
            } catch (InterruptedException e) {
                log.error("Error occurred while getting tweet", e);
            }

            if (Objects.nonNull(message)) {
                publisher.publish(topic, null, message);
            }
        }
    }

    public void shutDownStream() {
        log.info("Closing Tweet stream");
        client.stop();
    }
}
