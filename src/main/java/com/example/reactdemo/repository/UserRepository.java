package com.example.reactdemo.repository;

import com.example.reactdemo.dto.UserRequest;
import com.example.reactdemo.model.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Integer> {

    @Query("SELECT * FROM users u WHERE u.username like :name and u.email like :email")
    Flux<User> getAllUserByNameAndEmail(String name, String email);
}
