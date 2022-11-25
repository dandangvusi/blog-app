package com.dan.blogapp;

import com.dan.blogapp.entity.Role;
import com.dan.blogapp.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlogAppApplication {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(BlogAppApplication.class, args);
    }

//    implements CommandLineRunner if use this run method
//    @Autowired
//    private RoleRepository roleRepository;

//    @Override
//    public void run(String... args) throws Exception {
//        Role adminRole = new Role();
//        adminRole.setRolename("ROLE_ADMIN");
//        roleRepository.save(adminRole);
//        Role userRole = new Role();
//        userRole.setRolename("ROLE_USER");
//        roleRepository.save(userRole);
//    }
}
