package com.project.blogapp.entity.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.project.blogapp.entity.enums.Permission.*;

public enum Role {
    USER(
            Set.of(USER_READ, USER_CREATE, USER_UPDATE, USER_DELETE)
    ),
    ADMIN(
            Set.of(ADMIN_READ, ADMIN_CREATE, ADMIN_UPDATE, ADMIN_DELETE)
    ),

    MANAGER(Collections.EMPTY_SET);

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<SimpleGrantedAuthority> getUserAuthorities() {
        List<SimpleGrantedAuthority> authorities = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }
}
