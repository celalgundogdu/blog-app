package com.project.blogapp.data;

import com.github.javafaker.Faker;
import com.project.blogapp.entity.Image;
import com.project.blogapp.entity.Post;
import com.project.blogapp.entity.User;
import com.project.blogapp.entity.enums.Role;
import com.project.blogapp.repository.PostRepository;
import com.project.blogapp.repository.UserRepository;
import com.project.blogapp.service.ImageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SampleDataLoader implements CommandLineRunner {

    private final ImageService imageService;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    private final PasswordEncoder passwordEncoder;
    private final Faker faker;

    public SampleDataLoader(ImageService imageService, UserRepository userRepository, PostRepository postRepository,
                            PasswordEncoder passwordEncoder) {
        this.imageService = imageService;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.passwordEncoder = passwordEncoder;
        this.faker = new Faker();
    }

    @Override
    public void run(String... args) throws Exception {

        Image defaultUserImage = imageService.findDefaultUserImage();

        userRepository.deleteAll();
        postRepository.deleteAll();

        for (long i = 1; i <= 5; i++) {
            User user = userRepository.save(new User(
                    faker.name().fullName(),
                    faker.name().username(),
                    passwordEncoder.encode("Aa123456"),
                    defaultUserImage,
                    Role.USER));

            for (long j = 1; j <= 5; j++) {
                postRepository.save(new Post(
                        user,
                        faker.lorem().sentence(),
                        faker.lorem().paragraph()
                ));
            }
        }
    }
}
