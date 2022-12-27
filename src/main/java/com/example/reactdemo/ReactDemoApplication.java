package com.example.reactdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;


@EnableR2dbcRepositories
@SpringBootApplication
@EnableCaching
public class ReactDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactDemoApplication.class, args);

		List<String> list = new ArrayList<>();
		Flux.just("A","B","C")
				.log()
				.subscribe(list::add);
	}

}
