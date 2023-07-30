package com.project.blogapp.service;

import com.project.blogapp.dto.request.CreateCommentRequest;
import com.project.blogapp.dto.request.UpdateCommentRequest;
import com.project.blogapp.dto.response.CreateCommentResponse;
import com.project.blogapp.dto.response.GetAllCommentsResponse;
import com.project.blogapp.dto.response.GetCommentResponse;
import com.project.blogapp.dto.response.UpdateCommentResponse;
import com.project.blogapp.entity.Comment;
import com.project.blogapp.entity.Post;
import com.project.blogapp.entity.User;
import com.project.blogapp.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final UserService userService;
    private final PostService postService;
    private final CommentRepository commentRepository;

    public CommentService(UserService userService, PostService postService, CommentRepository commentRepository) {
        this.userService = userService;
        this.postService = postService;
        this.commentRepository = commentRepository;
    }

    public List<GetAllCommentsResponse> getAllComments(Optional<Long> userId, Optional<Long> postId) {
        List<Comment> commentList;
        if (userId.isPresent() && postId.isPresent()) {
            commentList = commentRepository.findByUserIdAndPostId(userId.get(), postId.get());
        } else if (userId.isPresent()) {
            commentList = commentRepository.findByUserId(userId.get());
        } else if (postId.isPresent()) {
            commentList = commentRepository.findByPostId(postId.get());
        } else {
            commentList = commentRepository.findAll();
        }

        List<GetAllCommentsResponse> response = commentList
                .stream()
                .map(comment -> GetAllCommentsResponse.convert(comment))
                .toList();

        return response;
    }

    public GetCommentResponse getCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        GetCommentResponse response = GetCommentResponse.convert(comment);
        return response;
    }

    public CreateCommentResponse createComment(CreateCommentRequest request) {
        User user = userService.findById(request.getUserId());
        Post post = postService.findById(request.getPostId());
        Comment comment = new Comment(0L, post, user, request.getText());
        Comment createdComment = commentRepository.save(comment);
        CreateCommentResponse response = CreateCommentResponse.convert(createdComment);
        return response;
    }

    public UpdateCommentResponse updateComment(Long id, UpdateCommentRequest request) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setText(request.getText());
        Comment updatedComment = commentRepository.save(comment);
        UpdateCommentResponse response = new UpdateCommentResponse(updatedComment.getId(), updatedComment.getText());
        return response;
    }

    public void deleteComment(Long id) {
        boolean exists = commentRepository.existsById(id);
        if (!exists) {
            throw new RuntimeException("Comment not found");
        }
        commentRepository.deleteById(id);
    }
}
