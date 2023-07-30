package com.project.blogapp.service;

import com.project.blogapp.constant.Messages;
import com.project.blogapp.dto.request.ChangeUserRoleRequest;
import com.project.blogapp.dto.request.CreateUserRequest;
import com.project.blogapp.dto.request.UpdateUserRequest;
import com.project.blogapp.dto.response.*;
import com.project.blogapp.entity.Image;
import com.project.blogapp.entity.User;
import com.project.blogapp.exception.EntityNotFoundException;
import com.project.blogapp.repository.UserRepository;
import com.project.blogapp.util.SystemHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    private final ImageService imageService;
    private final UserRepository userRepository;

    private final SystemHelper systemHelper;

    public UserService(ImageService imageService, UserRepository userRepository, SystemHelper systemHelper) {
        this.imageService = imageService;
        this.userRepository = userRepository;
        this.systemHelper = systemHelper;
    }

    public List<GetAllUsersResponse> getAllUsers() {
        List<User> userList;
        if (systemHelper.getCurrentUser() == null) {
            userList = userRepository.findAll();
        } else {
            userList = userRepository.findByUsernameNot(systemHelper.getCurrentUser().getUsername());
        }
        List<GetAllUsersResponse> response = userList
                .stream()
                .map(user -> GetAllUsersResponse.convert(user))
                .toList();

        return response;
    }

    public GetUserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Messages.User.NOT_FOUND));
        GetUserResponse response = GetUserResponse.convert(user);
        return response;
    }

    public CreateUserResponse createUser(CreateUserRequest request) {
        User user = new User(0L, request.getDisplayName(), request.getUsername(), request.getPassword());
        Image image = imageService.findDefaultUserImage();
        user.setImage(image);
        User createdUser = userRepository.save(user);
        CreateUserResponse response = CreateUserResponse.convert(createdUser);
        return response;
    }

    public UpdateUserResponse updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Messages.User.NOT_FOUND));
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        User updatedUser = userRepository.save(user);
        UpdateUserResponse response = new UpdateUserResponse(updatedUser.getId(), updatedUser.getUsername());
        return response;
    }

    public void deleteUser(Long id) {
        boolean exists = userRepository.existsById(id);
        if (!exists) {
            throw new EntityNotFoundException(Messages.User.NOT_FOUND);
        }
        userRepository.deleteById(id);
    }

    protected User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Messages.User.NOT_FOUND));
    }

    protected List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public UploadImageResponse uploadUserImage(Long userId, MultipartFile file) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(Messages.User.NOT_FOUND));
        Image imageToDelete = user.getImage();
        Image image = imageService.saveImage(file);
        user.setImage(image);
        userRepository.save(user);
        imageService.deleteImage(imageToDelete.getPublicId());
        UploadImageResponse response = UploadImageResponse.convert(image);
        return response;
    }

    public void deleteUserImage(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(Messages.User.NOT_FOUND));
        Image image = imageService.findDefaultUserImage();
        if (!Objects.equals(image.getId(), user.getImage().getId())) {
            imageService.deleteImage(user.getImage().getPublicId());
            user.setImage(image);
            userRepository.save(user);
        }
    }

    public void changeUserRole(Long id, ChangeUserRoleRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Messages.User.NOT_FOUND));
        user.setRole(request.getRole());
        userRepository.save(user);
    }
}
