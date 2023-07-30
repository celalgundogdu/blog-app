package com.project.blogapp.dto.response;

import com.project.blogapp.entity.Image;

public record UploadImageResponse(Long id,
                                  String url) {

    public static UploadImageResponse convert(Image from) {
        return new UploadImageResponse(from.getId(), from.getUrl());
    }
}
