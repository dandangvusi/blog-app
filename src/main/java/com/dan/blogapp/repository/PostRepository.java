package com.dan.blogapp.repository;

import com.dan.blogapp.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = "SELECT * FROM post p WHERE " +
            "p.title LIKE CONCAT('%', :searchKeyword, '%') " +
            "OR p.description LIKE CONCAT('%', :searchKeyword, '%') " +
            "OR p.content LIKE CONCAT('%', :searchKeyword, '%')",
            nativeQuery = true)
//    @Query("select p from Post p where concat(p.title, ' ', p.description, ' ', p.content) like %?1% ")
    Page<Post> searchPosts(String searchKeyword, Pageable pageable);
}