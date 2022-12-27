package com.example.reactdemo.configuration;

import com.github.benmanes.caffeine.cache.AsyncCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Configuration
public class Config  {

    @Bean
    AsyncCache<Object, Object> getCache(){
        return Caffeine.newBuilder()
                .maximumSize(10)
                .expireAfterWrite(30, TimeUnit.SECONDS)
                .buildAsync();
    }

    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
