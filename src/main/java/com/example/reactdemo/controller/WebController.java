package com.example.reactdemo.controller;

import com.example.reactdemo.service.WebService;
import com.example.reactdemo.wrapper.ResponseWrapper;
import com.github.benmanes.caffeine.cache.AsyncCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class WebController {

    @Autowired private WebService webService;

    @Autowired private AsyncCache<Object, Object> getCache;

    @Autowired private CacheManager cacheManager;


    @RequestMapping(value = "/deck/countAsyn", method = RequestMethod.GET)
    public Object randomActivityAsyn(ServerHttpRequest request) {

        Object obj = getCache.getIfPresent("Random Activity");
        if(obj != null) return obj;

        Mono<ResponseWrapper> responseWrapperMono = webService.activity()
                .map(result -> ResponseWrapper.successResponse("Random Activity", result, request.getPath().toString()))
                .defaultIfEmpty(ResponseWrapper.errorResponse("Internal server error", null, request.getPath().toString()));

        getCache.put("Random Activity", responseWrapperMono.toFuture());

        return responseWrapperMono;

    }


    @RequestMapping(value = "/deck/countSynSave", method = RequestMethod.GET)
    @Cacheable(value = "Activity-cache", key = "#root.methodName")
    public Object randomActivitySyn(ServerHttpRequest request) {
        return webService.activityResponseBlocked();
    }


}
