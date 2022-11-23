package com.dan.blogapp.controller;

import com.dan.blogapp.dto.PostDTO;
import com.dan.blogapp.dto.PostDTOv2;
import com.dan.blogapp.dto.PostResponse;
import com.dan.blogapp.service.PostService;
import com.dan.blogapp.utils.Constant;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(produces="application/json")
public class PostController {
    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(value = "/api/v1/posts", produces = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO){
        return new ResponseEntity<>(this.postService.createPost(postDTO), HttpStatus.CREATED);
    }

    @GetMapping(value = "/api/v1/posts", produces = "application/json")
    public ResponseEntity<PostResponse> getAllPost(
            @RequestParam(value = "pageNo", defaultValue = Constant.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = Constant.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = Constant.DEFAULT_SORT_FIELD, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = Constant.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return new ResponseEntity<>(this.postService.getAllPost(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping(value = "/api/v1/posts/{id}", produces = "application/json")
    public ResponseEntity<PostDTO> getPostById(@PathVariable(name = "id") long id){
        return new ResponseEntity<>(this.postService.getPostById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/api/v2/posts/{id}", produces = "application/json")
    public ResponseEntity<PostDTOv2> getPostByIdV2(@PathVariable(name = "id") long id){
        PostDTO postDTO = postService.getPostById(id);
        PostDTOv2 postDTOv2 = new PostDTOv2();
        postDTOv2.setId(postDTO.getId());
        postDTOv2.setTitle(postDTO.getTitle());
        postDTOv2.setDescription(postDTO.getDescription());
        postDTOv2.setContent(postDTO.getContent());
        postDTOv2.setComments(postDTO.getComments());
        List<String> tags = new ArrayList<>();
        tags.add("Java");
        tags.add("Spring");
        postDTOv2.setTags(tags);
        return new ResponseEntity<>(postDTOv2, HttpStatus.OK);
    }

    @GetMapping(value = "/api/posts/{id}", produces = "application/json", params = "version=3")
    public ResponseEntity<PostDTOv2> getPostByIdV3(@PathVariable(name = "id") long id){
        PostDTO postDTO = postService.getPostById(id);
        PostDTOv2 postDTOv2 = new PostDTOv2();
        postDTOv2.setId(postDTO.getId());
        postDTOv2.setTitle(postDTO.getTitle());
        postDTOv2.setDescription(postDTO.getDescription());
        postDTOv2.setContent(postDTO.getContent());
        postDTOv2.setComments(postDTO.getComments());
        List<String> tags = new ArrayList<>();
        tags.add("Java");
        tags.add("Spring");
        postDTOv2.setTags(tags);
        return new ResponseEntity<>(postDTOv2, HttpStatus.OK);
    }

    @GetMapping(value = "/api/posts/{id}", produces = "application/json", headers = "API-VERSION=4")
    public ResponseEntity<PostDTOv2> getPostByIdV4(@PathVariable(name = "id") long id){
        PostDTO postDTO = postService.getPostById(id);
        PostDTOv2 postDTOv2 = new PostDTOv2();
        postDTOv2.setId(postDTO.getId());
        postDTOv2.setTitle(postDTO.getTitle());
        postDTOv2.setDescription(postDTO.getDescription());
        postDTOv2.setContent(postDTO.getContent());
        postDTOv2.setComments(postDTO.getComments());
        List<String> tags = new ArrayList<>();
        tags.add("Java");
        tags.add("Spring");
        tags.add("api v4");
        postDTOv2.setTags(tags);
        return new ResponseEntity<>(postDTOv2, HttpStatus.OK);
    }

    @PutMapping(value = "/api/v1/posts/{id}", produces = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDTO> updatePostById(@Valid @RequestBody PostDTO postDTO, @PathVariable(name = "id") long id){
        return new ResponseEntity<>(this.postService.updatePostById(postDTO, id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/v1/posts/{id}", produces = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deletePostById(@PathVariable(name = "id") long id){
        this.postService.deletePostById(id);
        return new ResponseEntity<>("Delete post successfully", HttpStatus.OK);
    }
}
