package com.exp.service.tweet.feed.service.configuration;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticRestClient {

    @Bean
    public RestClientBuilder restClientBuilder(@Value("${elasticsearch.host:localhost}") String hostName,
                                               @Value("${elasticsearch.port:9200}") int port,
                                               @Value("${elasticsearch.scheme.protocol:http}") String httpProtocol) {
        return RestClient.builder(new HttpHost(hostName, port, httpProtocol));
    }

    @Bean
    public RestHighLevelClient getElasticSearchClient(RestClientBuilder restClientBuilder) {
        return new RestHighLevelClient(restClientBuilder);
    }
}
