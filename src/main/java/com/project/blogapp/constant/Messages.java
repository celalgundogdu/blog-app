package com.project.blogapp.constant;

public class Messages {

    public static class User {
        public static final String NOT_FOUND = "User not found";
        public static final String DUPLICATE_USERNAME = "Username already in use";
        public static final String INVALID_PASSWORD = "Password must be at least 8 characters and " +
                "contain at least one digit, one lower alpha character and one upper alpha character";
        public static final String INVALID_USERNAME_SIZE = "Username length must be between 2 and 25";
        public static final String INVALID_DISPLAYNAME_SIZE = "Display name length must be between 2 and 50";
    }

    public static class Post {
        public static final String NOT_FOUND = "Post not found";
    }

    public static class Comment {
        public static final String NOT_FOUND = "Comment not found";
    }

    public static class Like {
        public static final String NOT_FOUND = "Like not found";
    }

    public static class Image {
        public static final String NOT_FOUND = "Image not found";
        public static final String INVALID_EXTENSION = "Invalid file extension";
    }

    public static class Exception {
        public static final String VALIDATION = "Validation failed";
    }
}
