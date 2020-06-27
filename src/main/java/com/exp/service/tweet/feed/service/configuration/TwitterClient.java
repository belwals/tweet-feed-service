package com.exp.service.tweet.feed.service.configuration;

import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class TwitterClient {

    private final String consumerKey;
    private final String consumerSecret;
    private final String token;
    private final String secret;


    public TwitterClient(@Value("${twitter.consumerKey}") String consumerKey, @Value("${twitter.consumerSecret}") String consumerSecret,
                         @Value("${twitter.token}") String token, @Value("${twitter.secret}") String secret) {
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.token = token;
        this.secret = secret;
    }

    @Bean("tweetQueue")
    public BlockingQueue<String> getTweetQueue() {
        return new LinkedBlockingQueue<>(1000);
    }

    @Bean
    public Client getTwitterClient(@Qualifier("tweetQueue") BlockingQueue<String> queue) {

        List<String> searchQueries = Arrays.asList("kafka", "java", "elasticsearch");
        return buildTwitterClient(queue, searchQueries);
    }

    private Client buildTwitterClient(BlockingQueue<String> queue, List<String> searchQueries) {

        HttpHosts httpHosts = new HttpHosts(Constants.STREAM_HOST);

        StatusesFilterEndpoint filterEndpoint = new StatusesFilterEndpoint();
        filterEndpoint.trackTerms(searchQueries);
        Authentication authentication = new OAuth1(consumerKey, consumerSecret, token, secret);

        ClientBuilder builder = new ClientBuilder()
                .name("tweet-feed-pipeline-01")
                .hosts(httpHosts)
                .authentication(authentication)
                .endpoint(filterEndpoint)
                .processor(new StringDelimitedProcessor(queue));

        return builder.build();
    }
}
