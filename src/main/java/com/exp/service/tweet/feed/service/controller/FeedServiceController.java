package com.exp.service.tweet.feed.service.controller;

import com.exp.service.tweet.feed.service.service.TweetOrchestratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest")
public class FeedServiceController {

    private final TweetOrchestratorService twitterService;

    @GetMapping("/fetch-tweets")
    @Async
    public void fetchTweets(@RequestParam("searchQueries") List<String> searchQueries) {
        twitterService.getActiveConnectionAndPublish(searchQueries);
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
