package com.project.blogapp.repository;

import com.project.blogapp.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findByUrl(String url);

    void deleteByPublicId(String publicId);
}
