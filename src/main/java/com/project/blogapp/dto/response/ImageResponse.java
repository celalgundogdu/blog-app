package com.project.blogapp.dto.response;

import com.project.blogapp.entity.Image;

public record ImageResponse(String url) {

    public static ImageResponse convert(Image from) {
        return new ImageResponse(from.getUrl());
    }
}
