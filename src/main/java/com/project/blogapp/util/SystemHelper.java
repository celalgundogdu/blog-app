package com.project.blogapp.util;

import com.project.blogapp.constant.Messages;
import com.project.blogapp.entity.Post;
import com.project.blogapp.entity.User;
import com.project.blogapp.repository.PostRepository;
import com.project.blogapp.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class SystemHelper {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public SystemHelper(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = "";
        if (authentication != null) {
            username = authentication.getName();
        }
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(Messages.User.NOT_FOUND));
    }

    public boolean canModifyPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(EntityNotFoundException::new);
        return post.getUser().getId().equals(getCurrentUser().getId());
    }
}
