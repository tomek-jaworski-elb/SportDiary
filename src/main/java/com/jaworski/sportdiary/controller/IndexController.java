package com.jaworski.sportdiary.controller;

import com.jaworski.sportdiary.domain.Activity;
import com.jaworski.sportdiary.service.activity.ActivityRepository;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@AllArgsConstructor
@Controller
public class IndexController {

    static final Logger logger = LogManager.getLogger(IndexController.class);

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
