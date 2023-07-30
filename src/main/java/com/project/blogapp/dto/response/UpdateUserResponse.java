package com.project.blogapp.dto.response;

import com.project.blogapp.entity.User;

public record UpdateUserResponse(Long id,
                                 String username) {

    public static UpdateUserResponse convert(User from) {
        return new UpdateUserResponse(from.getId(), from.getUsername());
    }
}
