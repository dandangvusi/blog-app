package com.dan.blogapp.dto;

import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupDTO {
    private String name;
    private String username;
    private String email;
    private String password;
}
