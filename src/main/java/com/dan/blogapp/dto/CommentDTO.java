package com.dan.blogapp.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@ApiModel(description = "Information model for comment")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    @ApiModelProperty(value = "Comment id")
    private long id;
    @ApiModelProperty(value = "Name of user who write the comment")
    @NotEmpty(message = "name must not be empty")
    private String name;
    @ApiModelProperty(value = "Email of user who write the comment")
    @NotEmpty(message = "email must not be empty")
    @Email
    private String email;
    @ApiModelProperty(value = "Content of comment")
    @NotEmpty(message = "comment content should be at least 10 characters")
    @Size(min = 10)
    private String body;
}
