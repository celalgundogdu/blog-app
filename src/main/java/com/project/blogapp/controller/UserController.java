package com.project.blogapp.controller;

import com.project.blogapp.annotation.FileType;
import com.project.blogapp.dto.request.ChangeUserRoleRequest;
import com.project.blogapp.dto.request.CreateUserRequest;
import com.project.blogapp.dto.request.UpdateUserRequest;
import com.project.blogapp.dto.response.*;
import com.project.blogapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<GetAllUsersResponse>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
    public ResponseEntity<GetUserResponse> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        return new ResponseEntity<>(userService.createUser(request), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/image")
    @PreAuthorize("#id.equals(@systemHelper.currentUser.id)")
    public ResponseEntity<UploadImageResponse> uploadUserImage(@PathVariable Long id,
                                                               @FileType(types = {"jpg", "jpeg", "png"})
                                                               @RequestParam("image") MultipartFile file) {
        return new ResponseEntity<>(userService.uploadUserImage(id, file), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("#id.equals(@systemHelper.currentUser.id)")
    public ResponseEntity<UpdateUserResponse> updateUser(@PathVariable Long id,
                                                         @Valid @RequestBody UpdateUserRequest request) {
        return new ResponseEntity<>(userService.updateUser(id, request), HttpStatus.OK);
    }

    @PutMapping("/{id}/role")
    @PreAuthorize("hasRole('MANAGER')")
    public void changeRole(@PathVariable Long id, @Valid @RequestBody ChangeUserRoleRequest request) {
        userService.changeUserRole(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("#id.equals(@systemHelper.currentUser.id)")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @DeleteMapping("/{id}/image")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("#id.equals(@systemHelper.currentUser.id)")
    public void deleteUserImage(@PathVariable Long id) {
        userService.deleteUserImage(id);
    }
}
