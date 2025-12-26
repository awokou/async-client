package com.server.async.client.client;

import com.server.async.client.bean.Post;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class PostClient {

    private final WebClient webClient;

    public PostClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<Post> findAll() {
        return webClient.get()
                .uri("/posts")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(new RuntimeException("Client Error")))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        Mono.error(new RuntimeException("Server Error")))
                .bodyToFlux(Post.class)
                .onErrorResume(WebClientResponseException.class, e ->
                        Mono.error(new RuntimeException("Error: " + e.getResponseBodyAsString())));
    }

    public Mono<Post> findById(Integer id) {
        return webClient.get()
                .uri("/posts/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(new RuntimeException("Not Found")))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        Mono.error(new RuntimeException("Server Error")))
                .bodyToMono(Post.class)
                .onErrorResume(WebClientResponseException.class, e ->
                        Mono.error(new RuntimeException("Error: " + e.getResponseBodyAsString())));
    }

    public Mono<Post> create(Post post) {
        return webClient.post()
                .uri("/posts")
                .bodyValue(post)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(new RuntimeException("Client Error")))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        Mono.error(new RuntimeException("Server Error")))
                .bodyToMono(Post.class)
                .onErrorResume(WebClientResponseException.class, e ->
                        Mono.error(new RuntimeException("Error: " + e.getResponseBodyAsString())));
    }

    public Mono<Post> update(Integer id, Post post) {
        return webClient.put()
                .uri("/posts/{id}", id)
                .bodyValue(post)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(new RuntimeException("Not Found")))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        Mono.error(new RuntimeException("Server Error")))
                .bodyToMono(Post.class)
                .onErrorResume(WebClientResponseException.class, e ->
                        Mono.error(new RuntimeException("Error: " + e.getResponseBodyAsString())));
    }

    public Mono<Void> delete(Integer id) {
        return webClient.delete()
                .uri("/posts/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(new RuntimeException("Not Found")))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        Mono.error(new RuntimeException("Server Error")))
                .bodyToMono(Void.class)
                .onErrorResume(WebClientResponseException.class, e ->
                        Mono.error(new RuntimeException("Error: " + e.getResponseBodyAsString())));
    }
}
