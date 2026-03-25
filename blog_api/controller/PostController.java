package com.blogapi.controller;

import com.blogapi.model.dto.PostRequest;
import com.blogapi.model.dto.PostResponse;
import com.blogapi.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<Page<PostResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(postService.getAllPosts(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PostMapping
    public ResponseEntity<PostResponse> create(@Valid @RequestBody PostRequest request) {
        return ResponseEntity.ok(postService.createPost(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> update(@PathVariable Long id,
                                               @Valid @RequestBody PostRequest request) {
        return ResponseEntity.ok(postService.updatePost(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Deleted successfully");
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<PostResponse>> getByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(postService.getPostsByCategory(categoryId));
    }
}