package com.project.blogapp.dto.response;

import com.project.blogapp.entity.Post;

public record CreatePostResponse(Long id,
                                 String title,
                                 String text) {

    public static CreatePostResponse convert(Post from) {
        return new CreatePostResponse(from.getId(), from.getTitle(), from.getText());
    }
}
