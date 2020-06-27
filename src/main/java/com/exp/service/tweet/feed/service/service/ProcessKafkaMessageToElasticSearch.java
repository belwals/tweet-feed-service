package com.exp.service.tweet.feed.service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProcessKafkaMessageToElasticSearch {

//    private final RestHighLevelClient elasticClient;

    @KafkaListener(topics = {"tweet_topic"}, groupId = "KAFKA_PIPELINE_APP")
    public void listenKafkaMessagesAndPublishToElasticSearch(String message) throws IOException {
        IndexRequest request = new IndexRequest("twitter").source(message, XContentType.JSON);
        log.info("Received tweets : {}", message);
//        elasticClient.index(request, RequestOptions.DEFAULT);
    }
}
