package com.project.blogapp.dto.request;

import com.project.blogapp.entity.enums.Role;

public class ChangeUserRoleRequest {

    private Role role;

    public ChangeUserRoleRequest() {
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
