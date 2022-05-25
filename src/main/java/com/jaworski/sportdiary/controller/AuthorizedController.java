package com.jaworski.sportdiary.controller;

import com.jaworski.sportdiary.domain.Activity;
import com.jaworski.sportdiary.domain.ListParam;
import com.jaworski.sportdiary.entity.ActivityEntity;
import com.jaworski.sportdiary.entity.controll.DBActivityManager;
import com.jaworski.sportdiary.repository.ActivityRepository;
import com.jaworski.sportdiary.service.DBActivity.DBActivityLoader;
import com.jaworski.sportdiary.service.activity.ActivityService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.annotation.Secured;
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
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class AuthorizedController {

    private static final Logger logger = LogManager.getLogger(AuthorizedController.class);

    private final ActivityService activityService;
    private final ActivityRepository activityRepository;

    private final DBActivityManager dbActivityManager;

    private final DBActivityLoader dbActivityLoader;


    @GetMapping("/**")
    public String handle() {
        return "404";
    }

    @GetMapping(path = {"/list", "/", ""})
    public String list(@ModelAttribute(name = "listParam", value = "") ListParam listParam, @RequestParam(required = false) boolean save, @RequestParam(required = false) boolean error, Model model) {
        logger.info(listParam);

//        dbActivityLoader.loadDB();
//        List<ActivityEntity> all = dbActivityManager.findAll();
//        all.forEach(System.out::println);

        Comparator<Activity> comparator;
        switch (listParam.getSort()) {
            case "DISTANCE": {
                comparator = Comparator.comparingDouble(activity -> activity.getDistance().getDistanceKM());
                break;
            }
            case "ID": {
                comparator = Comparator.comparingLong(Activity::getId);
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
        model.addAttribute("save", save);
        model.addAttribute("error", error);
        return "list";
    }


    @GetMapping(value = "/edit", params = "id")
    @Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
    public String edit(@RequestParam(required = true, name = "id") int id, Model model) {
        Activity activity = activityService.getActivity(id);
        logger.info(activity);
        model.addAttribute("activity", activity);
        return "edit";
    }

    @PostMapping("/edit")
    @Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
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

    @Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping(value = "/delete", params = "id")
    public RedirectView delete(@RequestParam(required = true, name = "id") int id) {
        activityService.delete(id);
        return new RedirectView("/user");
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/save")
    public RedirectView saveList(Model model) {
        if (activityRepository.saveToJson()) {
            return new RedirectView("/user?save=true");
        } else {
            return new RedirectView("/user?error=true");
        }

    }

    @GetMapping(path = "/more", params = "id")
    public String more(@RequestParam(required = false) int id, @RequestParam(required = false) String lang, Model model) {

        model.addAttribute("activity", activityService.getActivity(id));
        return "more";
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/add")
    public String add(Model model) {
        Activity activity = new Activity();
        Long nextId = activityService.maxId() + 1;
        activity.setId(nextId);
        model.addAttribute("activity", activity);
        return "add";
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
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
