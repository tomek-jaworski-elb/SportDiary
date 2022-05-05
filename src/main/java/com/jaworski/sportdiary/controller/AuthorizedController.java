package com.jaworski.sportdiary.controller;

import com.jaworski.sportdiary.domain.Activity;
import com.jaworski.sportdiary.domain.ListParam;
import com.jaworski.sportdiary.service.activity.ActivityRepository;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class AuthorizedController {

    static final Logger logger = LogManager.getLogger(AuthorizedController.class);

    private ActivityRepository activityRepository;

    @GetMapping(path = {"/list", "/", ""})
    public String list(@ModelAttribute() ListParam listParam, Model model) {
        logger.info(listParam.toString());
        Comparator<Activity> comparator = (activity, t1) -> {
            return 0;
        };
        switch (listParam.getSort()) {
            case "DISTANCE": {
                comparator = (Activity a1, Activity a2) -> {
                    return (int) (a1.getDistance().getDistanceKM() - a2.getDistance().getDistanceKM());
                };
                break;
            }
            case "ID": {
                comparator = Comparator.comparingInt(Activity::getId);
                break;
            }
            case "DATE": {
                comparator = Comparator.comparing(Activity::getDateTime);
                break;
            }
            case "SPORT": {
                comparator = Comparator.comparing((Activity a) -> a.getSport().getName());
                break;
            }
            case "DURATION": {
                comparator = Comparator.comparing(Activity::getDuration);
                break;
            }
        }
        List<Activity> list = activityRepository.sort(comparator);
        model.addAttribute("activities", list);
        model.addAttribute("listParam", new ListParam());
        return "list";
    }

    @PostMapping(path = "/add")
    public String newActivity(@Valid @ModelAttribute Activity activity, BindingResult result, Model model) {
        logger.info(activity);
        if (result.hasErrors()) {
            logger.info(result);
            return "add";
        } else {
            activityRepository.addActivity(activity);
            model.addAttribute("activity", activity);
            return "new";
        }
    }

    @GetMapping(value = "/delete", params = "id")
    public RedirectView delete(@RequestParam(required = true, defaultValue = "", name = "id") int id) {
        activityRepository.delete(id);
        return new RedirectView("/user");
    }

    @GetMapping("/save")
    public RedirectView saveList(Model model) {
        activityRepository.saveToJson();
        return new RedirectView("/user");
    }

    @GetMapping(path = "/more", params = "id")
    public String more(@RequestParam int id, Model model) {

        model.addAttribute("activity", activityRepository.getActivities().stream()
                .filter(activity -> activity.getId() == id)
                .findFirst()
                .orElse(new Activity()));
        return "more";
    }

    @GetMapping("/add")
    public String add(Model model) {
        Activity activity = new Activity();
        int nextId = activityRepository.maxId() + 1;
        activity.setId(nextId);
        model.addAttribute("activity", activity);
        return "add";
    }
}
