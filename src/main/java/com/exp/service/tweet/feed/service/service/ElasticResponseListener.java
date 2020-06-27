package com.exp.service.tweet.feed.service.service;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexResponse;

@Slf4j
public class ElasticResponseListener<T extends IndexResponse> implements ActionListener {

    @Override
    public void onResponse(Object o) {
        IndexResponse response = (IndexResponse) o;
        log.info("Indexed tweet with id={} received from Twitter", response.getId());
    }

    @Override
    public void onFailure(Exception e) {
        log.error("Error occurred while publishing tweet to ElasticSearch");
    }
}
