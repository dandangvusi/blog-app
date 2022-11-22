package com.dan.blogapp.service;

import com.dan.blogapp.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    CommentDTO createComment(long postId, CommentDTO commentDTO);
    List<CommentDTO> getCommentByPostId(long postId);
    CommentDTO getCommentById(long postId, long commentId);

    CommentDTO updateCommentById(long postId, long commentId, CommentDTO commentDTO);
    void deleteCommentById(long postId, long commentId);
}
