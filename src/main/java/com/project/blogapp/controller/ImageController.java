package com.project.blogapp.controller;

import com.project.blogapp.dto.response.ImageResponse;
import com.project.blogapp.dto.response.UploadImageResponse;
import com.project.blogapp.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageResponse> getImage(@PathVariable Long id) {
        return new ResponseEntity<>(imageService.getImage(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UploadImageResponse> uploadImage(@RequestParam("image") MultipartFile file) {
        return new ResponseEntity<>(imageService.uploadImage(file), HttpStatus.CREATED);
    }
}
