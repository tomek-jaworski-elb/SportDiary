package com.jaworski.sportdiary.controller;

import com.jaworski.sportdiary.domain.Activity;
import com.jaworski.sportdiary.domain.User;
import com.jaworski.sportdiary.entity.SportEntity;
import com.jaworski.sportdiary.entity.UserEntity;
import com.jaworski.sportdiary.entity.controll.DBEntityManager;
import com.jaworski.sportdiary.mapper.UserMapper;
import com.jaworski.sportdiary.service.activity.ActivityService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private static final Logger logger = LogManager.getLogger(IndexController.class);

    private final ActivityService activityService;
    private final DBEntityManager<SportEntity> dbSportEntityManager;
    private final DBEntityManager<UserEntity> dbUserEntityManager;
    private final UserMapper userMapper;


    @GetMapping(path = {"/", "welcome", "index"})
    public String index(Model model) {
        List<Activity> list = activityService.getActivityList();
        model.addAttribute("activities", list);
        return "welcome";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/test")
    public String test(Model model) {
        SportEntity sportEntity = dbSportEntityManager.find(SportEntity.class, 1L);
//        System.out.println(sportEntity);
        // TODO: 2020-04-24
        sportEntity = dbSportEntityManager.find(1L);
//        System.out.println(sportEntity);
        model.addAttribute("User", new User());
        return "test";
    }

    @PostMapping("/test")
    public String addUser(@ModelAttribute User user) {
        System.out.println(user);
        UserEntity userEntity = userMapper.mapToUserEntity(user);
        System.out.println(userEntity);
        dbUserEntityManager.save(userEntity);
        return "redirect:/test";
    }

    @GetMapping("/403")
    public String error403() {
        return "403";
    }


}
