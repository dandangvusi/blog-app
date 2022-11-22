package com.dan.blogapp.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private long id;
//    title shoud not be null and contain atleast 2 characters
    @NotEmpty
    @Size(min = 2, message = "title must have atleast 2 characters.")
    private String title;
//    description should not be null and contain atleast 10 characters
    @NotEmpty
    @Size(min = 10, message = "description must have atleast characters.")
    private String description;
    @NotEmpty
    private String content;
    private List<CommentDTO> comments;
}
