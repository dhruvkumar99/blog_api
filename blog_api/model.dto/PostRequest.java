package com.blogapi.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequest {

    @NotNull
    private String title;

    @NotNull
    private String content;

    private String author;

    @NotNull
    private Long categoryId;
}