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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController()
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
@Validated
public class IndexRestController {

    private final UserService userService;
    private final ActivityService activityService;
    private final MyBasicAuthenticationConverter basicAuthenticationConverter;

    @GetMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @GetMapping(path = "/acts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Activity>> getAllActivities() {
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
