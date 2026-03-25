package com.blogapi.service;

import com.blogapi.exception.ResourceNotFoundException;
import com.blogapi.model.dto.PostRequest;
import com.blogapi.model.dto.PostResponse;
import com.blogapi.model.entity.Category;
import com.blogapi.model.entity.Post;
import com.blogapi.repository.CategoryRepository;
import com.blogapi.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    public Page<PostResponse> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable).map(this::mapToResponse);
    }

    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        return mapToResponse(post);
    }

    public PostResponse createPost(PostRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setAuthor(request.getAuthor());
        post.setCategory(category);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(postRepository.save(post));
    }

    public PostResponse updatePost(Long id, PostRequest request) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setAuthor(request.getAuthor());
        post.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(postRepository.save(post));
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public List<PostResponse> getPostsByCategory(Long categoryId) {
        return postRepository.findByCategoryId(categoryId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private PostResponse mapToResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .categoryId(post.getCategory().getId())
                .categoryName(post.getCategory().getName())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}