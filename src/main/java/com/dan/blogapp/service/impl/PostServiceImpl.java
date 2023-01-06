package com.dan.blogapp.service.impl;

import com.dan.blogapp.dto.PostDTO;
import com.dan.blogapp.dto.PostResponse;
import com.dan.blogapp.entity.Post;
import com.dan.blogapp.exception.ResourceNotFoundException;
import com.dan.blogapp.repository.PostRepository;
import com.dan.blogapp.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private ModelMapper modelMapper;
    @Autowired
    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper){
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Post post = DTO2Entity(postDTO);
        Post res = this.postRepository.save(post);
        PostDTO dto = Entity2DTO(res);
        return dto;
    }

    @Override
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = this.postRepository.findAll(pageable);
        List<Post> listOfPosts = posts.getContent();
        List<PostDTO> dtoList = listOfPosts.stream().map(post -> Entity2DTO(post)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(dtoList);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElement(posts.getTotalElements());
        postResponse.setTotalPage(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
        LinkedList<String> l = null;
        return postResponse;

    }

    @Override
    public PostDTO getPostById(long id) {
        Post post = this.postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return Entity2DTO(post);
    }

    @Override
    public PostDTO updatePostById(PostDTO postDTO, long id) {
        Post post = this.postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());
        Post updated_post = this.postRepository.save(post);
        return Entity2DTO(updated_post);
    }

    @Override
    public PostResponse searchPost(int pageNo, int pageSize, String sortBy, String sortDir, String searchKeyword) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = this.postRepository.searchPosts(searchKeyword, pageable);
        List<Post> postsList = posts.getContent();
        List<PostDTO> dtoList = postsList.stream().map(post -> Entity2DTO(post)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(dtoList);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElement(posts.getTotalElements());
        postResponse.setTotalPage(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
        return postResponse;
    }

    @Override
    public void deletePostById(long id) {
        Post post = this.postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        this.postRepository.delete(post);
    }

    private Post DTO2Entity(PostDTO postDTO){
        Post post = this.modelMapper.map(postDTO, Post.class);
        return post;
    }
    private PostDTO Entity2DTO(Post post){
        PostDTO dto = this.modelMapper.map(post, PostDTO.class);
        return dto;
    }
}
