package com.exp.service.tweet.feed.service.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:runtime.properties")
public class PropertiesFileLoader {
}
