package com.dan.blogapp.controller;

import com.dan.blogapp.dto.PostDTO;
import com.dan.blogapp.dto.PostResponse;
import com.dan.blogapp.service.PostService;
import com.dan.blogapp.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/posts", produces="application/json")
public class PostController {
    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(produces = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO){
        return new ResponseEntity<>(this.postService.createPost(postDTO), HttpStatus.CREATED);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<PostResponse> getAllPost(
            @RequestParam(value = "pageNo", defaultValue = Constant.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = Constant.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = Constant.DEFAULT_SORT_FIELD, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = Constant.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return new ResponseEntity<>(this.postService.getAllPost(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<PostDTO> getPostById(@PathVariable(name = "id") long id){
        return new ResponseEntity<>(this.postService.getPostById(id), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDTO> updatePostById(@Valid @RequestBody PostDTO postDTO, @PathVariable(name = "id") long id){
        return new ResponseEntity<>(this.postService.updatePostById(postDTO, id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deletePostById(@PathVariable(name = "id") long id){
        this.postService.deletePostById(id);
        return new ResponseEntity<>("Delete post successfully", HttpStatus.OK);
    }
}
