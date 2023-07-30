package com.project.blogapp.dto.response;

import com.project.blogapp.entity.Post;

import java.util.List;

public record GetPostResponse(Long id,
                              String title,
                              String text,
                              List<GetAllLikesResponse> postLikes,
                              List<GetAllCommentsResponse> postComments) {

    public static GetPostResponse convert(Post from) {
        List<GetAllLikesResponse> postLikesResponse = from.getLikes()
                .stream()
                .map(like -> GetAllLikesResponse.convert(like))
                .toList();

        List<GetAllCommentsResponse> postCommentsResponse = from.getComments()
                .stream()
                .map(comment -> GetAllCommentsResponse.convert(comment))
                .toList();

        return new GetPostResponse(
                from.getId(),
                from.getTitle(),
                from.getText(),
                postLikesResponse,
                postCommentsResponse
        );
    }
}
