package com.dan.blogapp.service.impl;

import ch.qos.logback.core.rolling.helper.Compressor;
import com.dan.blogapp.dto.CommentDTO;
import com.dan.blogapp.dto.PostResponse;
import com.dan.blogapp.entity.Comment;
import com.dan.blogapp.entity.Post;
import com.dan.blogapp.exception.BlogAPIException;
import com.dan.blogapp.exception.ResourceNotFoundException;
import com.dan.blogapp.repository.CommentRepository;
import com.dan.blogapp.repository.PostRepository;
import com.dan.blogapp.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private ModelMapper modelMapper;
    private PostRepository postRepository;
    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper){
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public CommentDTO createComment(long postId, CommentDTO commentDTO) {
        Comment comment = DTO2Entity(commentDTO);
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        comment.setPost(post);
        Comment rtnComment = this.commentRepository.save(comment);
        CommentDTO res = Entity2DTO(rtnComment);
        return res;
    }

    @Override
    public List<CommentDTO> getCommentByPostId(long postId) {
        List<Comment> comments = this.commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> Entity2DTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDTO getCommentById(long postId, long commentId) {
        Post post = this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
        Comment comment = this.commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "id", commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        return Entity2DTO(comment);
    }

    @Override
    public CommentDTO updateCommentById(long postId, long commentId, CommentDTO commentDTO) {
        Post post = this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
        Comment comment = this.commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "id", commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        comment.setEmail(commentDTO.getEmail());
        comment.setName(commentDTO.getName());
        comment.setBody(commentDTO.getBody());
        Comment res = this.commentRepository.save(comment);
        return Entity2DTO(res);
    }

    @Override
    public void deleteCommentById(long postId, long commentId) {
        Post post = this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
        Comment comment = this.commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "id", commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        this.commentRepository.delete(comment);
    }

    private Comment DTO2Entity(CommentDTO commentDTO){
        Comment comment = this.modelMapper.map(commentDTO, Comment.class);
        return comment;
    }

    private CommentDTO Entity2DTO(Comment comment){
        CommentDTO dto = this.modelMapper.map(comment, CommentDTO.class);
        return dto;
    }
}
