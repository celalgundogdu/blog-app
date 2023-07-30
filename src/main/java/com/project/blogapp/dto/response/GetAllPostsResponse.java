package com.project.blogapp.dto.response;

import com.project.blogapp.entity.Post;

import java.util.List;

public record GetAllPostsResponse(Long id,
                                  Long userId,
                                  String username,
                                  String title,
                                  String text,
                                  List<GetAllLikesResponse> postLikes) {

    public static GetAllPostsResponse convert(Post from) {
        List<GetAllLikesResponse> postLikesResponse = from.getLikes()
                .stream()
                .map(like -> GetAllLikesResponse.convert(like))
                .toList();

        return new GetAllPostsResponse(
                from.getId(),
                from.getUser().getId(),
                from.getUser().getUsername(),
                from.getTitle(),
                from.getText(),
                postLikesResponse
        );
    }
}
