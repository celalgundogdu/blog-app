package com.project.blogapp.dto;

public class UserReportDto {

    private Long id;
    private String displayName;
    private String username;

    public UserReportDto(Long id, String displayName, String username) {
        this.id = id;
        this.displayName = displayName;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
