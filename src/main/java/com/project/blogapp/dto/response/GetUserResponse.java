package com.project.blogapp.dto.response;

import com.project.blogapp.entity.User;

public record GetUserResponse(Long id,
                              String username) {

    public static GetUserResponse convert(User from) {
        return new GetUserResponse(from.getId(), from.getUsername());
    }
}
