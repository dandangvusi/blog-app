package com.dan.blogapp.controller;

import com.dan.blogapp.dto.LoginDTO;
import com.dan.blogapp.dto.SignupDTO;
import com.dan.blogapp.entity.Role;
import com.dan.blogapp.entity.User;
import com.dan.blogapp.repository.RoleRepository;
import com.dan.blogapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder){
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDTO loginDTO){
        Authentication auth_obj = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth_obj);
        return new ResponseEntity<>("Logged in successfully", HttpStatus.OK);
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupDTO signupDTO){
        if(this.userRepository.existsByUsername(signupDTO.getUsername())){
            return new ResponseEntity<>("Username has registered by another account!", HttpStatus.BAD_REQUEST);
        }
        else if (this.userRepository.existsByEmail(signupDTO.getEmail())){
            return new ResponseEntity<>("Email has registered by another account!", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setName(signupDTO.getName());
        user.setUsername(signupDTO.getUsername());
        user.setEmail(signupDTO.getEmail());
        user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
        Role role = this.roleRepository.findByRolename("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(role));
        this.userRepository.save(user);
        return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
    }
}
