package com.project.blogapp.dto.response;

import com.project.blogapp.entity.User;

public record GetAllUsersResponse(Long id,
                                  String displayName,
                                  String username) {

    public static GetAllUsersResponse convert(User from) {
        return new GetAllUsersResponse(from.getId(), from.getDisplayName(), from.getUsername());
    }
}
