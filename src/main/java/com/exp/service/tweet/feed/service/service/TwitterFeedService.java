package com.exp.service.tweet.feed.service.service;

import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;

import static org.springframework.util.CollectionUtils.isEmpty;

@Configuration
public class TwitterFeedService {

    private final Authentication authentication;
    private final BlockingQueue<String> blockingQueue;
    private final HttpHosts httpHosts;
    private final StatusesFilterEndpoint statusesFilterEndpoint;

    public TwitterFeedService(Authentication authentication, @Qualifier("tweetQueue") BlockingQueue<String> blockingQueue,
                              HttpHosts httpHosts, StatusesFilterEndpoint statusesFilterEndpoint) {
        this.authentication = authentication;
        this.blockingQueue = blockingQueue;
        this.httpHosts = httpHosts;
        this.statusesFilterEndpoint = statusesFilterEndpoint;
    }

    private Client client;
    private List<String> currentSearchQueries;


    public synchronized Client getTwitterClient(List<String> searchQueries) {
        if (checkIfNewSearchQueries(searchQueries)) {
            stopClientConnection();
            currentSearchQueries = searchQueries;
            statusesFilterEndpoint.trackTerms(searchQueries);
            ClientBuilder builder = new ClientBuilder()
                    .name("tweet-feed-pipeline-01")
                    .hosts(httpHosts)
                    .authentication(authentication)
                    .endpoint(statusesFilterEndpoint)
                    .processor(new StringDelimitedProcessor(blockingQueue));
            client = builder.build();
            client.connect();
        }
        return client;
    }

    private boolean checkIfNewSearchQueries(List<String> searchQueries) {
        if (isEmpty(searchQueries)) {
            throw new RuntimeException("");
        }
        return !searchQueries.equals(currentSearchQueries);
    }

    public void stopClientConnection() {
        if (Objects.nonNull(client)) {
            client.stop();
        }
    }
}
