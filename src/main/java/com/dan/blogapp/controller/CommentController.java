package com.dan.blogapp.controller;

import com.dan.blogapp.dto.CommentDTO;
import com.dan.blogapp.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "CRUD APIs for comment resource")
@RestController
@RequestMapping(value = "/api/v1/posts/{postId}/comments", produces = "application/json")
public class CommentController {
    private CommentService commentService;
    @Autowired
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }
    @ApiOperation(value = "Create comment REST API")
    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<CommentDTO> createComment(@Valid @RequestBody CommentDTO commentDTO,
                                        @PathVariable(name = "postId") long postId){
        return new ResponseEntity(this.commentService.createComment(postId, commentDTO), HttpStatus.CREATED);
    }
    @ApiOperation(value = "Get all comments by post id REST API")
    @GetMapping
    public ResponseEntity<List<CommentDTO>> getCommentByPostId(@PathVariable(name = "postId") Long postId){
        return new ResponseEntity<>(this.commentService.getCommentByPostId(postId), HttpStatus.OK);
    }
    @ApiOperation(value = "Get comment by id REST API")
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable(name = "commentId") long commentId, @PathVariable(name = "postId") long postId){
        return new ResponseEntity<>(this.commentService.getCommentById(postId, commentId), HttpStatus.OK);
    }
    @ApiOperation(value = "Update comment by id REST API")
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDTO> updateCommentById(@PathVariable(name = "commentId") long commentId, @PathVariable(name = "postId") long postId, @Valid @RequestBody CommentDTO commentDTO){
        return new ResponseEntity<>(this.commentService.updateCommentById(postId, commentId, commentDTO), HttpStatus.OK);
    }
    @ApiOperation(value = "Delete comment by id REST API")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteCommentById(@PathVariable(name = "postId") long postId, @PathVariable(name = "commentId") long commentId){
        this.commentService.deleteCommentById(postId, commentId);
        return new ResponseEntity<>("Successfully deleted comment with id: " + commentId, HttpStatus.OK);
    }
}
