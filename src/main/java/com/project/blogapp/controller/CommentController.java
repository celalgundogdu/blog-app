package com.project.blogapp.controller;

import com.project.blogapp.dto.request.CreateCommentRequest;
import com.project.blogapp.dto.request.UpdateCommentRequest;
import com.project.blogapp.dto.response.CreateCommentResponse;
import com.project.blogapp.dto.response.GetAllCommentsResponse;
import com.project.blogapp.dto.response.GetCommentResponse;
import com.project.blogapp.dto.response.UpdateCommentResponse;
import com.project.blogapp.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<GetAllCommentsResponse>> getAllComments(@RequestParam Optional<Long> userId,
                                                                       @RequestParam Optional<Long> postId) {
        return new ResponseEntity<>(commentService.getAllComments(userId, postId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCommentResponse> getCommentById(@PathVariable Long id) {
        return new ResponseEntity<>(commentService.getCommentById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CreateCommentResponse> createComment(@Valid @RequestBody CreateCommentRequest request) {
        return new ResponseEntity<>(commentService.createComment(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateCommentResponse> updateComment(@PathVariable Long id,
                                                               @Valid @RequestBody UpdateCommentRequest request) {
        return new ResponseEntity<>(commentService.updateComment(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }
}
