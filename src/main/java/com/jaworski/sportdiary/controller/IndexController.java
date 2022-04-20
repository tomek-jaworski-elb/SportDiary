package com.jaworski.sportdiary.controller;

import com.jaworski.sportdiary.domain.Activity;
import com.jaworski.sportdiary.service.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private ActivityRepository activityRepository;

    @GetMapping(path = {"/", "/index"})
    public String index(Model model) {
        List<Activity> list = activityRepository.getRepository();
        model.addAttribute("activities", list);
        return "index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("new_activity", new Activity());
        return "add";
    }


    @PostMapping("/add")
    public String newActivity(@Valid @ModelAttribute("activity") Activity activity, Model model) {
        activityRepository.addActivity(activity);
        model.addAttribute("activity", activity.toString());
        return "new";
    }

}
