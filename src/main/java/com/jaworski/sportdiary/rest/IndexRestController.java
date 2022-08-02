package com.jaworski.sportdiary.rest;

import com.jaworski.sportdiary.domain.Activity;
import com.jaworski.sportdiary.domain.User;
import com.jaworski.sportdiary.entity.ActivityEntity;
import com.jaworski.sportdiary.entity.UserEntity;
import com.jaworski.sportdiary.mapper.ActivityMapper;
import com.jaworski.sportdiary.mapper.UserMapper;
import com.jaworski.sportdiary.repository.ActivityEntityRepository;
import com.jaworski.sportdiary.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController()
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
@Validated
public class IndexRestController {

    private final UserEntityRepository userEntityRepository;
    private final ActivityEntityRepository activityEntityRepository;
    private final UserMapper userMapper;
    private final ActivityMapper activityMapper;

    @GetMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getUserEntityList(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth, @AuthenticationPrincipal Principal principal) {
        // TODO: check if user is admin
        System.out.println(auth);
//        System.out.println(principal.getName());
        List<UserEntity> all = userEntityRepository.findAll();
        List<User> users = all.stream().map(userEntity -> {
            User user = new User();
            user.setEmail(userEntity.getEmail());
            user.setId(userEntity.getId());
            user.setFirstName(userEntity.getFirstName());
            user.setRoles(userEntity.getRoles().stream().reduce((s, s2) -> s + "," + s2).orElse("USER"));
            return user;
        }).toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping(path = "/acts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Activity>> getAllActivities(@RequestHeader(HttpHeaders.AUTHORIZATION) HttpHeaders auth) {
        if (auth.get(HttpHeaders.AUTHORIZATION) == null) {
            return ResponseEntity.status(401).build();
        }
        // TODO: check if user is admin
        List<String> strings = auth.get(HttpHeaders.AUTHORIZATION);
        strings.forEach(System.out::println);
        List<ActivityEntity> all = activityEntityRepository.findAll();
        List<Activity> activities = new ArrayList<>();
        all.forEach(activityEntity -> {
            Activity activity = activityMapper.EntityToActivity(activityEntity);
            activities.add(activity);
        });
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/acts/{id}")
    public ResponseEntity<Activity> getActivity(@PathVariable UUID id) {
        return ResponseEntity.of(activityEntityRepository.findById(id)
                .map(activityMapper::EntityToActivity));
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
