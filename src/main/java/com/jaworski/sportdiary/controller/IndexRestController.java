package com.jaworski.sportdiary.controller;

import com.jaworski.sportdiary.entity.ActivityEntity;
import com.jaworski.sportdiary.entity.UserEntity;
import com.jaworski.sportdiary.entity.repository.ActivityEntityRepository;
import com.jaworski.sportdiary.entity.repository.UserEntityRepository;
import com.jaworski.sportdiary.service.AuthenticationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
public class IndexRestController {

    private final UserEntityRepository userEntityRepository;
    private final AuthenticationService authenticationService;
    private final ActivityEntityRepository activityEntityRepository;

    public IndexRestController(UserEntityRepository userEntityRepository, AuthenticationService authenticationService, ActivityEntityRepository activityEntityRepository) {
        this.userEntityRepository = userEntityRepository;
        this.authenticationService = authenticationService;
        this.activityEntityRepository = activityEntityRepository;
    }

    @GetMapping("/users")
    public List<UserEntity> getUserEntityList() {
        return userEntityRepository.findAll();
    }

    @GetMapping("/acts")
    public List<ActivityEntity> users() {
        List<ActivityEntity> activityEntities = new ArrayList<>();
        Iterable<ActivityEntity> all = activityEntityRepository.findAll();
        all.forEach(activityEntities::add);
        return activityEntities;
    }

    @GetMapping("/user/activities")
    public List<ActivityEntity> getActivityEntityList() {
        return (List<ActivityEntity>) activityEntityRepository.findAll();
    }
}
