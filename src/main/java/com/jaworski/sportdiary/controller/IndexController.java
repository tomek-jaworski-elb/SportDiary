package com.jaworski.sportdiary.controller;

import com.jaworski.sportdiary.service.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @Autowired
    private ActivityRepository activityRepository;

    @GetMapping(path = {"/", "/index"})
    public String index(Model model) {
        model.addAttribute("activities", activityRepository.getRepository());
        return "index";
    }
}
