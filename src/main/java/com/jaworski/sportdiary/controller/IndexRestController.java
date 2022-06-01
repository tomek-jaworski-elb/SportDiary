package com.jaworski.sportdiary.controller;

import com.jaworski.sportdiary.entity.ActivityEntity;
import com.jaworski.sportdiary.entity.UserEntity;
import com.jaworski.sportdiary.entity.repository.ActivityEntityRepository;
import com.jaworski.sportdiary.entity.repository.UserEntityRepository;
import com.jaworski.sportdiary.service.AuthenticationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;

@RestController
public class IndexRestController {

    private UserEntityRepository userEntityRepository;
    private AuthenticationService authenticationService;
    private ActivityEntityRepository activityEntityRepository;

    public IndexRestController(UserEntityRepository userEntityRepository, AuthenticationService authenticationService, ActivityEntityRepository activityEntityRepository) {
        this.userEntityRepository = userEntityRepository;
        this.authenticationService = authenticationService;
        this.activityEntityRepository = activityEntityRepository;
    }

    @GetMapping("/users")
    public List<UserEntity> getUserEntityList() {
        return userEntityRepository.findAll();
    }

    @GetMapping("/user/log")
    public String users() {
        return authenticationService.getCurrentUserName() + System.lineSeparator() + authenticationService.getPrincipal();
    }

    @GetMapping("/user/activities")
    public List<ActivityEntity> getActivityEntityList() {
        return (List<ActivityEntity>) activityEntityRepository.findAll();
    }
}
