package com.jaworski.sportdiary.controller;

import com.jaworski.sportdiary.domain.Activity;
import com.jaworski.sportdiary.service.activity.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private ActivityRepository activityRepository;

    @GetMapping(path = {"/"})
    public String index(Model model) {
        List<Activity> list = activityRepository.getRepository();
        model.addAttribute("activities", list);
        return "welcome";
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
