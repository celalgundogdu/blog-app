package com.project.blogapp.dto.response;

import com.project.blogapp.entity.Comment;

public record GetAllCommentsResponse(Long id,
                                     String text) {

    public static GetAllCommentsResponse convert(Comment from) {
        return new GetAllCommentsResponse(from.getId(), from.getText());
    }
}
