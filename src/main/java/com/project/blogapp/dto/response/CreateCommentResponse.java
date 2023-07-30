package com.project.blogapp.dto.response;

import com.project.blogapp.entity.Comment;

public record CreateCommentResponse(Long id,
                                    String text) {

    public static CreateCommentResponse convert(Comment from) {
        return new CreateCommentResponse(from.getId(), from.getText());
    }
}
