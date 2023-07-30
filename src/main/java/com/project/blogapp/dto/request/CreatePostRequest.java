package com.project.blogapp.dto.request;

import jakarta.validation.constraints.NotBlank;

public class CreatePostRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String text;


    public CreatePostRequest() {
    }

    public CreatePostRequest(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
