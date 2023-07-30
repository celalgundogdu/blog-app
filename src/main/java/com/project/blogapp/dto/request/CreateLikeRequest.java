package com.project.blogapp.dto.request;

import jakarta.validation.constraints.NotNull;

public class CreateLikeRequest {

    @NotNull
    private Long postId;

    @NotNull
    private Long userId;

    public CreateLikeRequest() {
    }

    public CreateLikeRequest(Long postId, Long userId) {
        this.postId = postId;
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
