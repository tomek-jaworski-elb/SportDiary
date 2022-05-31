package com.jaworski.sportdiary.controller;

import com.jaworski.sportdiary.entity.UserEntity;
import com.jaworski.sportdiary.config.security.UserEntityRepository;
import com.jaworski.sportdiary.service.AuthenticationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexRestController {

    private UserEntityRepository userEntityRepository;
    private AuthenticationService authenticationService;

    public IndexRestController(UserEntityRepository userEntityRepository, AuthenticationService authenticationService) {
        this.userEntityRepository = userEntityRepository;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/users")
    public List<UserEntity> getUserEntityList() {
        return userEntityRepository.findAll();
    }

    @GetMapping("/user/log")
    public String users() {
        return authenticationService.getCurrentUserName() + System.lineSeparator() + authenticationService.getPrincipal();
    }
}
