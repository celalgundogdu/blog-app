package com.project.blogapp.controller;

import com.project.blogapp.dto.request.CreateLikeRequest;
import com.project.blogapp.dto.response.CreateLikeResponse;
import com.project.blogapp.dto.response.GetAllLikesResponse;
import com.project.blogapp.service.LikeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping
    public ResponseEntity<List<GetAllLikesResponse>> getAllLikes(@RequestParam Optional<Long> userId,
                                                                 @RequestParam Optional<Long> postId) {
        return new ResponseEntity<>(likeService.getAllLikes(userId, postId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CreateLikeResponse> createLike(@Valid @RequestBody CreateLikeRequest request) {
        return new ResponseEntity<>(likeService.createLike(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLike(@PathVariable Long id) {
        likeService.deleteLike(id);
    }

}
