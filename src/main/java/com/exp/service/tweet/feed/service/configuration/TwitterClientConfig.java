package com.exp.service.tweet.feed.service.configuration;

import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class TwitterClientConfig {
    private final String consumerKey;
    private final String consumerSecret;
    private final String token;
    private final String secret;

    public TwitterClientConfig(@Value("${twitter.consumerKey}") String consumerKey, @Value("${twitter.consumerSecret}") String consumerSecret,
                               @Value("${twitter.token}") String token, @Value("${twitter.secret}") String secret) {
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.token = token;
        this.secret = secret;
    }

    @Bean
    public Authentication getAuthentication() {
        return new OAuth1(consumerKey, consumerSecret, token, secret);
    }

    @Bean("tweetQueue")
    public BlockingQueue<String> getTweetQueue() {
        return new LinkedBlockingQueue<>(1000);
    }

    @Bean
    public HttpHosts HttpHosts() {
        return new HttpHosts(Constants.STREAM_HOST);
    }

    @Bean
    public StatusesFilterEndpoint getStatusFilterEndpoint() {
        return new StatusesFilterEndpoint();
    }
}
