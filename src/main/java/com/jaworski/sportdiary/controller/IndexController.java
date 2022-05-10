package com.jaworski.sportdiary.controller;

import com.jaworski.sportdiary.domain.Activity;
import com.jaworski.sportdiary.service.activity.ActivityService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private static final Logger logger = LogManager.getLogger(IndexController.class);

    private final ActivityService activityService;

    @GetMapping(path = {"/"})
    public String index(Model model) {
        List<Activity> list = activityService.getActivityList();
        model.addAttribute("activities", list);
        return "welcome";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
