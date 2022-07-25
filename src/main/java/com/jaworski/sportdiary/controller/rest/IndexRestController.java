package com.jaworski.sportdiary.controller;

import com.jaworski.sportdiary.entity.ActivityEntity;
import com.jaworski.sportdiary.entity.UserEntity;
import com.jaworski.sportdiary.entity.repository.ActivityEntityRepository;
import com.jaworski.sportdiary.entity.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class IndexRestController {

    private final UserEntityRepository userEntityRepository;
    private final ActivityEntityRepository activityEntityRepository;

    @GetMapping("/users")
    public List<UserEntity> getUserEntityList() {
        return userEntityRepository.findAll();
    }

    @GetMapping("/acts")
    public List<ActivityEntity> users() {
        return activityEntityRepository.findAll();
    }

    @GetMapping("/acts/{id}")
    public ActivityEntity getActivity(@PathVariable UUID id) {
        Optional<ActivityEntity> byId = activityEntityRepository.findById(id);
        return byId.orElse(new ActivityEntity());
    }
}
