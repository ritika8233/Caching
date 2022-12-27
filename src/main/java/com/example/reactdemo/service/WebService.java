package com.example.reactdemo.service;

import com.example.reactdemo.dto.ActivityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Service
public class WebService {

    @Autowired RestTemplate restTemplate;

    public Mono<ActivityResponse> activity(){
        WebClient webClient = WebClient.builder()
                .baseUrl("https://www.boredapi.com/api/activity")
                .build();
        return webClient.get().retrieve().bodyToMono(ActivityResponse.class);
    }

    public ActivityResponse activityResponseBlocked(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        return restTemplate.exchange("https://www.boredapi.com/api/activity", HttpMethod.GET, entity, ActivityResponse.class).getBody();
    }


}
