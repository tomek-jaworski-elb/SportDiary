package com.jaworski.sportdiary.controller;

import com.jaworski.sportdiary.domain.Activity;
import com.jaworski.sportdiary.service.ActivityRepository;
import com.jaworski.sportdiary.service.gson.JsonSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private ActivityRepository activityRepository;

    @GetMapping(path = {"/", "/index"})
    public String index(Model model) {
        model.addAttribute("activities", activityRepository.getRepository());
        List<Activity> list = activityRepository.getRepository();
        new JsonSaver(list);
        return "index";
    }
}
