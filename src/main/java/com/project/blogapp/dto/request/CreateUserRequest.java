package com.project.blogapp.dto.request;

import com.project.blogapp.annotation.Password;
import com.project.blogapp.annotation.UniqueUsername;
import com.project.blogapp.constant.Messages;
import jakarta.validation.constraints.Size;

public class CreateUserRequest {

    @Size(min = 2, max = 50, message = Messages.User.INVALID_DISPLAYNAME_SIZE)
    private String displayName;

    @Size(min = 2, max = 25, message = Messages.User.INVALID_USERNAME_SIZE)
    @UniqueUsername
    private String username;

    @Password
    private String password;

    public CreateUserRequest() {
    }

    public CreateUserRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
