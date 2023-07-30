package com.project.blogapp.dto.response;

import com.project.blogapp.entity.Comment;

public record GetCommentResponse(Long id,
                                 String text) {

    public static GetCommentResponse convert(Comment from) {
        return new GetCommentResponse(from.getId(), from.getText());
    }
}
