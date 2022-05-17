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
import java.util.Objects;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class AuthorizedController {

    private static final Logger logger = LogManager.getLogger(AuthorizedController.class);

    private final ActivityService activityService;
    private final ActivityRepository activityRepository;

    @GetMapping(path = {"/list", "/", ""})
    public String list(@ModelAttribute() ListParam listParam, Model model) {
        logger.info(listParam);
        Comparator<Activity> comparator;
        System.out.println(listParam.getIsAscending());
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
                if (Boolean.FALSE.equals(listParam.getIsAscending())) {
                    comparator = Comparator.comparing(Activity::getDateTime).reversed();
                    break;
                }
                    comparator = Comparator.comparing(Activity::getDateTime);
                break;
            }
            case "SPORT": {
                if (Boolean.FALSE.equals(listParam.getIsAscending())) {
                    comparator = Comparator.comparing((Activity a) -> a.getSport().getName()).reversed();
                    break;
                }
                comparator = Comparator.comparing((Activity a) -> a.getSport().getName());
                break;
            }
            case "DURATION": {
                comparator = Comparator.comparing(Activity::getDuration);
                break;
            }
            default: {
                comparator = Comparator.comparing(Objects::nonNull);
            }
        }
        List<Activity> list = activityService.sort(comparator);
        model.addAttribute("activities", list);
        model.addAttribute("listParam", listParam);
        return "list";
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
            model.addAttribute("activities", activityService.getActivityList());
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

        model.addAttribute("activity", activityService.getActivity(id));
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
}
