package com.project.blogapp.repository;

import com.project.blogapp.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUserId(Long userId);

    Page<Post> findByIdLessThan(Long id, Pageable page);

    @Query("SELECT p FROM Post p " +
            "WHERE " + "p.title LIKE CONCAT('%',:title, '%')")
    List<Post> searchPostsByTitle(String title);

    List<Post> findByIdGreaterThan(long id, Sort sort);

    long countByIdGreaterThan(long id);
}
