package com.dan.blogapp.dto;

import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    private String usernameOrEmail;
    private String password;
}
