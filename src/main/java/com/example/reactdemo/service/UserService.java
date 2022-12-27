package com.example.reactdemo.service;

import com.example.reactdemo.dto.ActivityResponse;
import com.example.reactdemo.dto.UserRequest;
import com.example.reactdemo.exception.DataValidationException;
import com.example.reactdemo.repository.UserRepository;
import com.example.reactdemo.dto.UserResponse;
import com.example.reactdemo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;

    public Mono<User> createUser(User user){
        return userRepository.save(user);
    }

    public Mono<List<UserResponse>> getAllUser(String name, String email){
        Flux<User> userFlux = userRepository.getAllUserByNameAndEmail("%"+name+"%", "%"+email+"%");

        return userFlux.map(user -> {
            return new UserResponse(user.getId(), user.getUsername(), user.getEmail());
        }).collectList();
    }

    public Mono<UserResponse> updateUser(UserRequest userRequest){
        Mono<User> user = userRepository.findById(userRequest.getId())
                .switchIfEmpty(Mono.error(new DataValidationException("User Not Found")));


        return user.flatMap(user1 -> {
                    if(userRequest.getEmail()!=null) {
                        user1.setEmail(userRequest.getEmail());
                    }
                    if(userRequest.getUsername()!=null) {
                        user1.setUsername(userRequest.getUsername());
                    }
                    return userRepository.save(user1);
                })
        .map(user1 -> {
            return new UserResponse(user1.getId(), user1.getUsername(), user1.getEmail());
        });

    }

}

/*
*
* return user.map(new Function<User, UserResponse>() {
                            @Override
                            public UserResponse apply(User user) {
                                if( userRequest.getEmail()!=null) {
                                    user.setEmail(userRequest.getEmail());
                                }
                                if (userRequest.getUsername()!=null) {
                                    user.setUsername(userRequest.getUsername());
                                }
                                userRepository.save(user);
                                return new UserResponse(user.getId(), user.getUsername(), user.getEmail());
                            }
                        });
*
* */