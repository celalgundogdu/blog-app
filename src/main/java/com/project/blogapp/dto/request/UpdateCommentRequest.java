package com.project.blogapp.dto.request;

import jakarta.validation.constraints.NotBlank;

public class UpdateCommentRequest {

    @NotBlank
    private String text;

    public UpdateCommentRequest() {
    }

    public UpdateCommentRequest(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
