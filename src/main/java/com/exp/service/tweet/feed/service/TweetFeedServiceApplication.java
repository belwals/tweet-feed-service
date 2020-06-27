package com.exp.service.tweet.feed.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
public class TweetFeedServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TweetFeedServiceApplication.class, args);
    }
}
