package com.jaworski.sportdiary.controller;

import com.jaworski.sportdiary.domain.Activity;
import com.jaworski.sportdiary.domain.ListParam;
import com.jaworski.sportdiary.repository.ActivityRepository;
import com.jaworski.sportdiary.service.activity.ActivityService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class AuthorizedController {

    static final Logger logger = LogManager.getLogger(AuthorizedController.class);

    private final ActivityRepository activityRepository;
    private final ActivityService activityService;

    @GetMapping(path = {"/list", "/", ""})
    public String list(@ModelAttribute() ListParam listParam, Model model) {
        logger.info(listParam);
        Comparator<Activity> comparator = (activity, t1) -> 0;
        switch (listParam.getSort()) {
            case "DISTANCE": {
                comparator = Comparator.comparingDouble(activity -> activity.getDistance().getDistanceKM());
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
        List<Activity> list = activityService.sort(comparator);
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
            activityService.addActivity(activity);
            model.addAttribute("activity", activity);
            return "new";
        }
    }

    @GetMapping(value = "/edit", params = "id")
    public String edit(@RequestParam(required = true, name = "id") int id, Model model) {
        Activity activity = activityService.getActivity(id);
        logger.info(activity);
        model.addAttribute("activity", activity);
        return "edit";
    }

    @PostMapping("/edit")
    public String edited(@Valid @ModelAttribute Activity activity, BindingResult result, Model model) {
        logger.info(activity);
        logger.error(result);
        if (result.hasErrors()) {
            return "edit";
        } else {
            activityService.update(activity.getId(), activity);
            model.addAttribute("activities", activityRepository.getRepository());
            model.addAttribute("listParam", new ListParam());
            return new RedirectView("/list").getUrl();
        }
    }

    @GetMapping(value = "/delete", params = "id")
    public RedirectView delete(@RequestParam(required = true, name = "id") int id) {
        activityService.delete(id);
        return new RedirectView("/user");
    }

    @GetMapping("/save")
    public RedirectView saveList(Model model) {
        activityRepository.saveToJson();
        return new RedirectView("/user");
    }

    @GetMapping(path = "/more", params = "id")
    public String more(@RequestParam int id, Model model) {

        model.addAttribute("activity", activityService.getActivityList().stream()
                .filter(activity -> activity.getId() == id)
                .findFirst()
                .orElse(new Activity()));
        return "more";
    }

    @GetMapping("/add")
    public String add(Model model) {
        Activity activity = new Activity();
        int nextId = activityService.maxId() + 1;
        activity.setId(nextId);
        model.addAttribute("activity", activity);
        return "add";
    }
}
