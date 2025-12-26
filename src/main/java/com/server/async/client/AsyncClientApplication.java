package com.server.async.client;

import com.server.async.client.bean.Post;
import com.server.async.client.client.PostClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class AsyncClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsyncClientApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(PostClient postClient) {
        return args -> {
            Flux<Post> posts = postClient.findAll();
            posts.subscribe(System.out::println);
        };
    }
}
