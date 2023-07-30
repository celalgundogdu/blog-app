package com.project.blogapp.dto.response;

import com.project.blogapp.entity.User;

public record CreateUserResponse(Long id,
                                 String username) {

    public static CreateUserResponse convert(User from) {
        return new CreateUserResponse(from.getId(), from.getUsername());
    }
}
