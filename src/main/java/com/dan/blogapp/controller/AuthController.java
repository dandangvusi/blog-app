package com.dan.blogapp.controller;

import com.dan.blogapp.dto.JwtAuthResponse;
import com.dan.blogapp.dto.LoginDTO;
import com.dan.blogapp.dto.SignupDTO;
import com.dan.blogapp.entity.Role;
import com.dan.blogapp.entity.User;
import com.dan.blogapp.repository.RoleRepository;
import com.dan.blogapp.repository.UserRepository;
import com.dan.blogapp.security.JwtTokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

@Api(value = "Authentication controller exposes sign in and sign up REST API")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder,
                          JwtTokenProvider jwtTokenProvider){
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    @ApiOperation(value = "REST API to sign in user to Blog app")
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> authenticateUser(@RequestBody LoginDTO loginDTO){
        Authentication auth_obj = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth_obj);
        // get jwt token from token provider
        String token = this.jwtTokenProvider.generateToken(auth_obj);
        return new ResponseEntity<>(new JwtAuthResponse(token), HttpStatus.OK);
    }
    @ApiOperation(value = "REST API to sign up user to Blog app")
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
        Role role = this.roleRepository.findByRolename("ROLE_USER").get();
        user.setRoles(Collections.singleton(role));
        this.userRepository.save(user);
        return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
    }
}
