package com.exp.service.tweet.feed.service.controller;

import com.exp.service.tweet.feed.service.service.TwitterService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest")
public class FeedServiceController {

    private final TwitterService twitterService;

    @GetMapping("/fetch-tweets")
    @Async
    public void fetchTweets() {
        twitterService.getActiveConnectionAndPublish();
    }

    @GetMapping("/stop-client")
    public void stopClient() {
        twitterService.shutDownStream();
    }

    @GetMapping("")
    public String welcomeWorld() {
        return "Hello World!";
    }
}
