package com.jaworski.sportdiary.rest;

import com.jaworski.sportdiary.config.websecurity.MyBasicAuthenticationConverter;
import com.jaworski.sportdiary.domain.Activity;
import com.jaworski.sportdiary.domain.User;
import com.jaworski.sportdiary.service.activity.ActivityService;
import com.jaworski.sportdiary.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class IndexRestController {

    private final UserService userService;
    private final ActivityService activityService;
    private final MyBasicAuthenticationConverter basicAuthenticationConverter;

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getUserEntityList(HttpServletRequest request,
                                                        @RequestHeader(HttpHeaders.AUTHORIZATION) HttpHeaders auth) {
        String requestHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (requestHeader == null || !requestHeader.startsWith("Basic ")) {
            throw new IllegalArgumentException("Basic authorization is required");
        }
        UsernamePasswordAuthenticationToken authentication = basicAuthenticationConverter.convert(request);

        if (authentication == null || authentication.getName() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        log.info("Auth: {}", authentication);
        User userCredentials = userService.getUserCredentials(authentication);
        log.info("User credentials: {}", userCredentials);
        log.info("auth: {}", auth);
        return ResponseEntity.ok(userService.getUserList());
    }

    @GetMapping(value = "/activities", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Activity>> getAllActivities(HttpServletRequest request) {
        User user = getUser(request);
        return ResponseEntity.ok(activityService.getUserActivities(user.getId()));
    }

    @PostMapping(value = "/activities", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Activity> createActivity(@Valid @RequestBody Activity activity, HttpServletRequest request) {
        log.info("activity: {}", activity);
        User userCredentials = getUser(request);
        activity.setUser(userCredentials);
        Activity activity1 = activityService.addActivity(activity, userCredentials.getId());
        return ResponseEntity.ok(activity1);
    }

    private User getUser(HttpServletRequest request) {
        String requestHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (requestHeader == null || !requestHeader.startsWith("Basic ")) {
            throw new IllegalArgumentException("Basic authorization is required");
        }
        log.info("request: {}", request);
        UsernamePasswordAuthenticationToken convert = basicAuthenticationConverter.convert(request);

        User userCredentials = userService.getUserCredentials(convert);
        log.info("userCredentials: id: {}, name: {}", userCredentials.getId(), userCredentials.getFirstName());
        return userCredentials;
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
