package com.example.reactdemo.controller;

import com.example.reactdemo.dto.UserRequest;
import com.example.reactdemo.dto.UserResponse;
import com.example.reactdemo.model.User;
import com.example.reactdemo.service.UserService;
import com.example.reactdemo.wrapper.ResponseWrapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired private UserService userService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Mono<ResponseWrapper> createUser(ServerHttpRequest request, @Valid @RequestBody User requestWrapper){
        User user = requestWrapper;
        return userService.createUser(user)
//                .map(result -> new ResponseWrapper<User>(null,"success","User added successfully", user))
                .map(result -> ResponseWrapper.successResponse("User added successfully", user, request.getPath().toString()))
                .defaultIfEmpty(ResponseWrapper.errorResponse("Internal server error",null, request.getPath().toString()));
    }


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Mono<ResponseWrapper<User>> getAllUsers(ServerHttpRequest request, @RequestParam String name, @RequestParam String email){
        Mono<List<UserResponse>> listMono = userService.getAllUser(name, email);

        return listMono.map(list -> {
//            return new ResponseWrapper(request.getPath().toString(), "true", "User List", list);
            return ResponseWrapper.successResponse("User List", list, request.getPath().toString());
        });

    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Mono<ResponseWrapper> updateUser(ServerHttpRequest request, @RequestBody UserRequest userRequest){
        return userService.updateUser(userRequest)
                .map(result -> ResponseWrapper.successResponse("User Updated", result, request.getPath().toString()));
    }

}
