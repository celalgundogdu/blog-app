package com.project.blogapp.controller;

import com.project.blogapp.dto.request.CreatePostRequest;
import com.project.blogapp.dto.request.UpdatePostRequest;
import com.project.blogapp.dto.response.CreatePostResponse;
import com.project.blogapp.dto.response.GetAllPostsResponse;
import com.project.blogapp.dto.response.GetPostResponse;
import com.project.blogapp.dto.response.UpdatePostResponse;
import com.project.blogapp.exception.EntityNotFoundException;
import com.project.blogapp.exception.ValidationExceptionResponse;
import com.project.blogapp.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/posts")
@Tag(name = "Post", description = "Post management APIs")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @Operation(
            method = "GET",
            summary = "Get All Posts REST API",
            description = "Get All Posts REST API is used to get all the posts from the database",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = GetAllPostsResponse.class)
                            )
                    )
            }
    )
    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<GetAllPostsResponse>> getAllPosts(@RequestParam Optional<Long> userId) {
        return new ResponseEntity<>(postService.getAllPosts(userId), HttpStatus.OK);
    }


    @GetMapping("/search")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<GetAllPostsResponse>> searchPostsByTitle(@RequestParam("title") String title){
        return ResponseEntity.ok(postService.searchPostsByTitle(title));
    }


    @Operation(
            method = "GET",
            summary = "Get Post By Id REST API",
            description = "Get Post By Id REST API is used to get a post from the database",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = GetPostResponse.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = EntityNotFoundException.class)
                            )
                    ),
            }
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GetPostResponse> getPostById(@PathVariable Long id) {
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }



    @GetMapping("/relative/{id}")
    public ResponseEntity<?> getPostsRelative(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable page,
            @PathVariable Long id,
            @RequestParam(name = "count", required = false, defaultValue = "false") boolean count,
            @RequestParam(name = "direction", defaultValue = "before") String direction
    ) {
        if (count) {
            long newPostsCount = postService.getNewPostsCount(id);
            Map<String, Long> response = new HashMap<>();
            response.put("count", newPostsCount);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        if (direction.equals("after")) {
            return new ResponseEntity<>(postService.getNewPosts(id, page.getSort()), HttpStatus.OK);
        }
        return new ResponseEntity<>(postService.getOldPosts(id, page), HttpStatus.OK);
    }


    @Operation(
            method = "POST",
            summary = "Create Post REST API",
            description = "Create Post REST API is used to create a new post",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CreatePostResponse.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ValidationExceptionResponse.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid token",
                            responseCode = "403"
                    )
            }
    )
    @PostMapping
    public ResponseEntity<CreatePostResponse> createPost(@Valid @RequestBody CreatePostRequest request) {
        return new ResponseEntity<>(postService.createPost(request), HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    @PreAuthorize("@systemHelper.canModifyPost(#id)")
    public ResponseEntity<UpdatePostResponse> updatePost(@PathVariable Long id,
                                                         @Valid @RequestBody UpdatePostRequest request) {
        return new ResponseEntity<>(postService.updatePost(id, request), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("@systemHelper.canModifyPost(#id)")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }

}
