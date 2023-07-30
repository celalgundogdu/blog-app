package com.project.blogapp.constant;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Constants {

    public static final Map CLOUDINARY_USER_PARAMS = ObjectUtils.asMap("folder", "socialapp/users");
    public static final Map CLOUDINARY_REPORT_PARAMS = ObjectUtils.asMap("folder", "socialapp/reports");

    public static final String DEFAULT_USER_IMAGE_URL =
            "https://res.cloudinary.com/daxsxs5ct/image/upload/v1687773023/socialapp/users/default_user.jpg";

    public static final String BEARER_PREFIX = "Bearer ";

    public static final long JWT_EXPIRATION = 1000 * 60 * 60 * 24;
    public static final long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24 * 7;

}
