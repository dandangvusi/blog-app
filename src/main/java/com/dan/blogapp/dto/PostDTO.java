package com.dan.blogapp.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@ApiModel(description = "Information model for Post")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    @ApiModelProperty(value = "post id")
    private long id;
//    title should not be null and contain at least 2 characters
    @ApiModelProperty(value = "post title")
    @NotEmpty
    @Size(min = 2, message = "title must have at least 2 characters.")
    private String title;
//    description should not be null and contain at least 10 characters
    @ApiModelProperty(value = "post description")
    @NotEmpty
    @Size(min = 10, message = "description must have at least characters.")
    private String description;
    @ApiModelProperty(value = "post content")
    @NotEmpty
    private String content;
    @ApiModelProperty(value = "List of comments belonging to the post")
    private List<CommentDTO> comments;
}
