package com.project.blogapp.dto.response;

import com.project.blogapp.entity.Like;

public record GetAllLikesResponse(Long id,
                                  Long postId,
                                  Long userId
                                  ) {

    public static GetAllLikesResponse convert(Like from) {
        return new GetAllLikesResponse(
                from.getId(),
                from.getPost().getId(),
                from.getUser().getId()
        );
    }
}
