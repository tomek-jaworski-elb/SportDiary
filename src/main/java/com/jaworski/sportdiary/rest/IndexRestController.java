package com.jaworski.sportdiary.rest;

import com.jaworski.sportdiary.config.websecurity.MyBasicAuthenticationConverter;
import com.jaworski.sportdiary.domain.Activity;
import com.jaworski.sportdiary.domain.User;
import com.jaworski.sportdiary.entity.UserEntity;
import com.jaworski.sportdiary.mapper.UserMapper;
import com.jaworski.sportdiary.repository.UserEntityRepository;
import com.jaworski.sportdiary.service.activity.ActivityService;
import com.jaworski.sportdiary.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    private final UserMapper userMapper;
    private final UserService userService;
    private final ActivityService activityService;
    private final MyBasicAuthenticationConverter basicAuthenticationConverter;

    @GetMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getUserEntityList(HttpServletRequest request,
                                                        @RequestHeader(HttpHeaders.AUTHORIZATION) HttpHeaders auth) {
        // TODO: check if user is admin
        UsernamePasswordAuthenticationToken convert = basicAuthenticationConverter.convert(request);
        log.info("convert: {}", convert);
        log.info("auth: {}", auth);
        // TODO: implement Service

        if (auth.get(HttpHeaders.AUTHORIZATION) == null) {
            return ResponseEntity.status(401).build();
        } else if (auth.get(HttpHeaders.AUTHORIZATION).get(0).equals("Basic YWRtaW46YWRtaW4=")) {
            log.info("auth 1 {}", auth.get(HttpHeaders.AUTHORIZATION).stream().toList());
            List<UserEntity> userEntityList = userEntityRepository.findAll();
            List<User> userList = new ArrayList<>();
            for (UserEntity userEntity : userEntityList) {
                userList.add(userMapper.toUser(userEntity));
            }
            return ResponseEntity.ok(userList);
        } else {
            log.info("auth 2 {}", auth.get(HttpHeaders.AUTHORIZATION).stream().toList());
            return ResponseEntity.status(401).build();
        }
    }

    @GetMapping(path = "/acts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Activity>> getAllActivities(@RequestHeader(HttpHeaders.AUTHORIZATION) HttpHeaders auth) {
        if (auth.get(HttpHeaders.AUTHORIZATION) == null) {
            return ResponseEntity.status(401).build();
        }
        // TODO: check if user is admin
        Optional<String> first = auth.get(HttpHeaders.AUTHORIZATION).stream().findFirst();
        if (!first.isPresent()) {
            return ResponseEntity.status(401).build();
        }
        String[] split = first.get().split(" ");
        String username = split[0];
        String password = split[1];
        if (!username.equals("admin") || !password.equals("admin")) {
            return ResponseEntity.status(401).build();
        }
        List<String> strings = auth.get(HttpHeaders.AUTHORIZATION);
        strings.forEach(System.out::println);
        return ResponseEntity.ok(activityService.getAllActivities());
    }

    @GetMapping("/acts/{id}")
    public ResponseEntity<Activity> getActivity(@PathVariable UUID id) {
        return ResponseEntity.of(Optional.ofNullable(activityService.getActivity(id)));
    }

    @GetMapping("/users/{id}/acts")
    public ResponseEntity<List<Activity>> getUserActivities(@PathVariable UUID id) {
        return ResponseEntity.ok(activityService.getUserActivities(id));
    }

}
