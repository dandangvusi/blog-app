package com.dan.blogapp.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private long id;
    @NotEmpty(message = "name must not be empty")
    private String name;
    @NotEmpty(message = "email must not be empty")
    @Email
    private String email;
    @NotEmpty(message = "comment content should be atleast 10 characters")
    @Size(min = 10)
    private String body;
}
