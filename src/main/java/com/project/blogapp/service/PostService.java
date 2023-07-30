package com.project.blogapp.service;

import com.project.blogapp.dto.request.CreatePostRequest;
import com.project.blogapp.dto.request.UpdatePostRequest;
import com.project.blogapp.dto.response.CreatePostResponse;
import com.project.blogapp.dto.response.GetAllPostsResponse;
import com.project.blogapp.dto.response.GetPostResponse;
import com.project.blogapp.dto.response.UpdatePostResponse;
import com.project.blogapp.entity.Post;
import com.project.blogapp.entity.User;
import com.project.blogapp.repository.PostRepository;
import com.project.blogapp.util.SystemHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final UserService userService;
    private final PostRepository postRepository;

    private final SystemHelper systemHelper;

    public PostService(UserService userService, PostRepository postRepository, SystemHelper systemHelper) {
        this.userService = userService;
        this.postRepository = postRepository;
        this.systemHelper = systemHelper;
    }

    public List<GetAllPostsResponse> getAllPosts(Optional<Long> userId) {
        List<Post> posts;
        if (userId.isPresent()) {
            posts = postRepository.findByUserId(userId.get());
        } else {
            posts = postRepository.findAll();
        }

        List<GetAllPostsResponse> response = posts.stream()
                .map(post -> GetAllPostsResponse.convert(post))
                .toList();

        return response;
    }

    public GetPostResponse getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow();
        GetPostResponse response = GetPostResponse.convert(post);
        return response;
    }

    public CreatePostResponse createPost(CreatePostRequest request) {
        User user = systemHelper.getCurrentUser();
        Post post = new Post(0L, user, request.getTitle(), request.getText());
        Post createdPost = postRepository.save(post);
        CreatePostResponse response = CreatePostResponse.convert(createdPost);
        return response;
    }

    public UpdatePostResponse updatePost(Long id, UpdatePostRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        post.setTitle(request.getTitle());
        post.setText(request.getText());
        Post updatedPost = postRepository.save(post);
        UpdatePostResponse response = UpdatePostResponse.convert(updatedPost);
        return response;
    }

    public void deletePost(Long id) {
        boolean exists = postRepository.existsById(id);
        if (!exists) {
            throw new RuntimeException("Post not found");
        }
        postRepository.deleteById(id);
    }

    protected Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public List<GetAllPostsResponse> searchPostsByTitle(String title) {
        List<Post> posts = postRepository.searchPostsByTitle(title);
        List<GetAllPostsResponse> response = posts.stream()
                .map(post -> GetAllPostsResponse.convert(post))
                .toList();
        return response;
    }

    public Page<GetAllPostsResponse> getOldPosts(Long id, Pageable page) {
        Page<Post> posts = postRepository.findByIdLessThan(id, page);
        return posts.map(post -> GetAllPostsResponse.convert(post));
    }

    public long getNewPostsCount(long id) {
        return postRepository.countByIdGreaterThan(id);
    }

    public List<GetAllPostsResponse> getNewPosts(long id, Sort sort) {
        List<Post> posts = postRepository.findByIdGreaterThan(id, sort);
        return posts.stream().map(post -> GetAllPostsResponse.convert(post)).toList();
    }
}
