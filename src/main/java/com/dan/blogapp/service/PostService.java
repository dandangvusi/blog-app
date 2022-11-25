package com.dan.blogapp.service;

import com.dan.blogapp.dto.PostDTO;
import com.dan.blogapp.dto.PostResponse;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);
    PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDTO getPostById(long id);
    PostDTO updatePostById(PostDTO postDTO, long id);
    PostResponse searchPost(int pageNo, int pageSize, String sortBy, String sortDir, String searchKeyword);

    void deletePostById(long id);
}
