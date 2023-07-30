package com.project.blogapp.dto.response;

import com.project.blogapp.entity.Post;

public record UpdatePostResponse(Long id,
                                 String title,
                                 String text) {

    public static UpdatePostResponse convert(Post from) {
        return new UpdatePostResponse(from.getId(), from.getTitle(), from.getText());
    }
}
