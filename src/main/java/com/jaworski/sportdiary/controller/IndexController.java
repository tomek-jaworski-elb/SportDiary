package com.jaworski.sportdiary.controller;

import com.jaworski.sportdiary.domain.Activity;
import com.jaworski.sportdiary.service.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

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
        Activity activity = new Activity();
        int nextId = activityRepository.maxId() + 1;
        activity.setId(nextId);
        model.addAttribute("activity", activity);
        return "add";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/add")
    public String newActivity(@Valid @ModelAttribute Activity activity, Model model) {
        activityRepository.addActivity(activity);
        model.addAttribute("activity", activity.toString());
        return "new";
    }

    @GetMapping("/save")
    public String saveList(Model model) {
        activityRepository.saveToJson();
        model.addAttribute("activities", activityRepository.getActivities());
        return "index";
    }

    @GetMapping("/more")
    public String more(@RequestParam int id, Model model) {

        model.addAttribute("activity", activityRepository.getActivities().stream()
                .filter(activity -> activity.getId() == id)
                .findFirst()
                .orElse(new Activity()));
        return "more";
    }

    @GetMapping(value = "/delete", params = "id")
    public RedirectView delete(@RequestParam(required = true, defaultValue = "", name = "id") int id) {
        activityRepository.delete(id);
        return new RedirectView("/");
    }
//    @PostMapping("/login_perform")
//    public RedirectView loginPerform() {
//        return new RedirectView("/");
//    }
}
