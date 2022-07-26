package com.jaworski.sportdiary.controller.rest;

import com.jaworski.sportdiary.domain.Activity;
import com.jaworski.sportdiary.domain.User;
import com.jaworski.sportdiary.domain.enums.Role;
import com.jaworski.sportdiary.entity.ActivityEntity;
import com.jaworski.sportdiary.entity.UserEntity;
import com.jaworski.sportdiary.entity.repository.ActivityEntityRepository;
import com.jaworski.sportdiary.entity.repository.UserEntityRepository;
import com.jaworski.sportdiary.mapper.ActivityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/api")
@RequiredArgsConstructor
public class IndexRestController {

    private final UserEntityRepository userEntityRepository;
    private final ActivityEntityRepository activityEntityRepository;
    private final ActivityMapper activityMapper;

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getUserEntityList() {
        List<UserEntity> all = userEntityRepository.findAll();
        List<User> users = all.stream().map(userEntity -> {
            User user = new User();
            user.setEmail(userEntity.getEmail());
            user.setId(userEntity.getId());
            user.setFirstName(userEntity.getFirstName());
            user.setRoles(userEntity.getRoles().stream().reduce((s, s2) -> s + "," + s2).get());
            return user;
        }).toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/acts")
    public ResponseEntity<List<Activity>> users() {
        List<ActivityEntity> all = activityEntityRepository.findAll();
        List<Activity> activities = new ArrayList<>();
        all.forEach(activityEntity -> {
            Activity activity = activityMapper.EntityToActivity(activityEntity);
            activities.add(activity);
        });
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/acts/{id}")
    public ResponseEntity<ActivityEntity> getActivity(@PathVariable UUID id) {
        return ResponseEntity.of(activityEntityRepository.findById(id));
    }

    @GetMapping("/users/{id}/acts")
    public ResponseEntity<List<Activity>> getUserActivities(@PathVariable UUID id) {
        List<ActivityEntity> activitiesByUserId = activityEntityRepository.findActivitiesByUserId(id);
        List<Activity> activities = new ArrayList<>();
        activitiesByUserId.forEach(activityEntity -> {
            Activity activity = activityMapper.EntityToActivity(activityEntity);
            activities.add(activity);
        });
        return ResponseEntity.ok(activities);
    }

}
