package com.server.async.client.controller;

import com.server.async.client.bean.Post;
import com.server.async.client.client.PostClient;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostClient postClient;

    public PostController(PostClient postClient) {
        this.postClient = postClient;
    }

    @GetMapping
    public Flux<Post> findAll() {
        return  postClient.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Post> findById(@PathVariable Integer id) {
        return postClient.findById(id);
    }

    @PostMapping
    public Mono<Post> createPost(@Valid @RequestBody Post post) {
        return postClient.create(post);
    }

    @PutMapping("/{id}")
    public Mono<Post> updatePost(@PathVariable Integer id, @RequestBody Post post) {
        return postClient.update(id, post);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deletePost(@PathVariable Integer id) {
        return postClient.delete(id);
    }
}
