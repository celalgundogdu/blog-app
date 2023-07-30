package com.project.blogapp.service;

import com.project.blogapp.dto.request.CreateLikeRequest;
import com.project.blogapp.dto.response.CreateLikeResponse;
import com.project.blogapp.dto.response.GetAllLikesResponse;
import com.project.blogapp.entity.Like;
import com.project.blogapp.entity.Post;
import com.project.blogapp.entity.User;
import com.project.blogapp.repository.LikeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeService {

    private final UserService userService;
    private final PostService postService;
    private final LikeRepository likeRepository;

    public LikeService(UserService userService, PostService postService, LikeRepository likeRepository) {
        this.userService = userService;
        this.postService = postService;
        this.likeRepository = likeRepository;
    }

    public List<GetAllLikesResponse> getAllLikes(Optional<Long> userId, Optional<Long> postId) {
        List<Like> likeList;
        if (userId.isPresent() && postId.isPresent()) {
            likeList = likeRepository.findByUserIdAndPostId(userId, postId);
        } else if (userId.isPresent()) {
            likeList = likeRepository.findByUserId(userId);
        } else if (postId.isPresent()) {
            likeList = likeRepository.findByPostId(postId);
        } else {
            likeList = likeRepository.findAll();
        }

        List<GetAllLikesResponse> response = likeList
                .stream()
                .map(like -> GetAllLikesResponse.convert(like))
                .toList();

        return response;
    }

    public CreateLikeResponse createLike(CreateLikeRequest request) {
        User user = userService.findById(request.getUserId());
        Post post = postService.findById(request.getPostId());
        Like like = new Like(0L, post, user);
        Like createdLike = likeRepository.save(like);
        CreateLikeResponse response = CreateLikeResponse.convert(createdLike);
        return response;
    }

    public void deleteLike(Long id) {
        boolean exists = likeRepository.existsById(id);
        if (!exists) {
            throw new RuntimeException("Like not found");
        }
        likeRepository.deleteById(id);
    }


}
