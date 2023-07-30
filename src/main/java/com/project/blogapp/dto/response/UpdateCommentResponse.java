package com.project.blogapp.dto.response;

import com.project.blogapp.entity.Comment;

public record UpdateCommentResponse(Long id,
                                    String text) {

    public static UpdateCommentResponse convert(Comment from) {
        return new UpdateCommentResponse(from.getId(), from.getText());
    }
}
