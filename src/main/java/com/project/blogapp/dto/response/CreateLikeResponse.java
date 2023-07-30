package com.project.blogapp.dto.response;

import com.project.blogapp.entity.Like;

public record CreateLikeResponse(Long id) {

    public static CreateLikeResponse convert(Like from) {
        return new CreateLikeResponse(from.getId());
    }
}
