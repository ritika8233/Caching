package com.example.reactdemo.configuration;

import com.example.reactdemo.exception.DataValidationException;
import com.example.reactdemo.wrapper.ResponseWrapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice(annotations = RestController.class)
public class ValidationHandler implements ApplicationEventPublisherAware {

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ResponseWrapper<String>> handleException(WebExchangeBindException e) {

        var errors = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        ResponseWrapper res = util(errors);

        return ResponseEntity.badRequest().body(res);
    }

    public ResponseWrapper util(List<String> errors){
        return ResponseWrapper.errorResponse("Bad Request", errors, "/api/user/all");
    }

    protected ApplicationEventPublisher eventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher=eventPublisher;
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataValidationException.class)
    public @ResponseBody ResponseWrapper handleDataStoreException(DataValidationException ex, ServerHttpRequest request) {
        return ResponseWrapper.errorResponse(ex.getMessage(), null, request.getPath().toString());
    }
}
