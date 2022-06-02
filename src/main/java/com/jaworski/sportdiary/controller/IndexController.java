package com.jaworski.sportdiary.controller;

import com.jaworski.sportdiary.domain.Activity;
import com.jaworski.sportdiary.entity.ActivityEntity;
import com.jaworski.sportdiary.entity.controll.DBEntityManager;
import com.jaworski.sportdiary.mapper.ActivityMapper;
import com.jaworski.sportdiary.service.activity.ActivityService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@AllArgsConstructor
@Controller
public class IndexController {

    private static final Logger LOGGER = LogManager.getLogger(IndexController.class);

    private ActivityService activityService;
//    private final DBEntityManager<SportEntity> dbSportEntityManager;
//    private final DBEntityManager<UserEntity> dbUserEntityManager;
    private  DBEntityManager<ActivityEntity> dbActivityEntityManager;
    private  ActivityMapper activityMapper;


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

//    @GetMapping("/test")
//    public String test(Model model) {
//        SportEntity sportEntity = dbSportEntityManager.find(SportEntity.class, 1L);
////        System.out.println(sportEntity);
//        // TODO: 2020-04-24
//        sportEntity = dbSportEntityManager.find(SportEntity.class, 1L);
////        System.out.println(sportEntity);
//        model.addAttribute("User", new User());
//        return "test";
//    }
//
//    @PostMapping("/test")
//    public String addUser(@ModelAttribute User user) {
//        System.out.println(user);
//        return "redirect:/test";
//    }

    @GetMapping("/add")
    public String addActivity(Model model) {
        model.addAttribute("activity", new Activity());
        return "add_activity";
    }

    @PostMapping("/add")
    public String add_Activity(@ModelAttribute Activity activity) {
        System.out.println(activity);
        ActivityEntity activityEntity = activityMapper.ActivityToEntity(activity);
        System.out.println(activityEntity);
        dbActivityEntityManager.save(activityEntity);
        return "redirect:/add";
    }


    @GetMapping("/403")
    public String error403() {
        return "403";
    }


}
