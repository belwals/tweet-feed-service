package com.exp.service.tweet.feed.service.service;

import com.twitter.hbc.core.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;

@Slf4j
@Service
public class TweetOrchestratorService {

    private final CustomKafkaProducer publisher;
    private final String topic;
    private final BlockingQueue<String> blockingQueue;
    private final TwitterFeedService twitterFeedService;

    public TweetOrchestratorService(CustomKafkaProducer publisher, @Value("${tweets.topic}") String topic,
                                    @Qualifier("tweetQueue") BlockingQueue<String> blockingQueue, TwitterFeedService twitterFeedService) {
        this.publisher = publisher;
        this.topic = topic;
        this.blockingQueue = blockingQueue;
        this.twitterFeedService = twitterFeedService;
    }

    public void getActiveConnectionAndPublish(List<String> searchQueries) {
        Client client = twitterFeedService.getTwitterClient(searchQueries);
        log.info("Connecting Streams...");
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
        twitterFeedService.stopClientConnection();
    }
}
