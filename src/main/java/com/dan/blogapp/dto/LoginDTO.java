package com.dan.blogapp.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(description = "Information model for login object")
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    @ApiModelProperty(value = "username or email of user")
    private String usernameOrEmail;
    @ApiModelProperty(value = "password of user")
    private String password;
}
