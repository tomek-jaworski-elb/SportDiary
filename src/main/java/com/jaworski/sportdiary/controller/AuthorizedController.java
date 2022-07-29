package com.jaworski.sportdiary.controller;

import com.jaworski.sportdiary.config.security.UserPrincipal;
import com.jaworski.sportdiary.domain.Activity;
import com.jaworski.sportdiary.domain.ListParam;
import com.jaworski.sportdiary.repository.ActivityRepository;
import com.jaworski.sportdiary.service.activity.ActivityService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.result.view.RedirectView;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.IntStream;

@Controller
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class AuthorizedController {

    private static final Logger LOGGER = LogManager.getLogger(AuthorizedController.class);

    private final ActivityService activityService;
    private final ActivityRepository activityRepository;

    @GetMapping("/**")
    public String handle() {
        return "404";
    }

    @GetMapping(path = {"/list", "/", ""})
    public String list(@ModelAttribute(name = "listParam", value = "") ListParam listParam,
                       @RequestParam(required = false) boolean save,
                       @RequestParam(required = false) boolean error,
                       @AuthenticationPrincipal UserPrincipal userPrincipal,
                       Model model) {
        LOGGER.info(listParam);
        activityService.setActivityList();
        Comparator<Activity> comparator;
        switch (listParam.getSort()) {
            case "DISTANCE": {
                comparator = Comparator.comparingDouble(activity -> activity.getDistance().getDistanceKM());
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
        List<Activity> list = activityService.getUserActivityList(userPrincipal, false);
        model.addAttribute("activities", list);
        model.addAttribute("listParam", listParam);
        model.addAttribute("save", save);
        model.addAttribute("error", error);
        return "list";
    }

    @GetMapping(path = "/list/all")
    @Secured("ROLE_ADMIN")
    public String listAll(Model model,
                          @RequestParam("page") Optional<Integer> page,
                          @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Activity> activityPage = activityService.getPage(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("activitiesPage", activityPage);
        model.addAttribute("activities", activityPage.getContent());
        int totalPages = activityPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .toList();
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("currentPage", activityPage.getPageable().getPageNumber() + 1);
        }
        model.addAttribute("listParam", new ListParam());
        model.addAttribute("save", null);
        model.addAttribute("error", null);
        return "list";
    }

    @GetMapping(value = "/edit", params = "id")
    @Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
    public String edit(@RequestParam(required = true, name = "id") UUID id, Model model) {
        Activity activity = activityService.getActivity(id);
        model.addAttribute("activity", activity);
        return "edit";
    }

    @PostMapping("/edit")
    @Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
    public String edited(@Valid @ModelAttribute Activity activity, BindingResult result, Model model) {
        LOGGER.info(activity);
        LOGGER.error(result);
        if (result.hasErrors()) {
            return "edit";
        } else {
            activityService.update(activity);
            model.addAttribute("activities", activityService.getActivityList());
            model.addAttribute("listParam", new ListParam());
            return "redirect:/user/list";
        }
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping(value = "/delete", params = "id")
    public String delete(@RequestParam(required = true, name = "id") UUID id,
                         @AuthenticationPrincipal UserPrincipal userPrincipal) {
        activityService.delete(id);
        if (userPrincipal.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equalsIgnoreCase("ADMIN"))) {
            return "redirect:/user/list/all";
        } else {
            return "redirect:/user/list";
        }
    }

    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping(value = "/restore", params = "id")
    public String restore(@RequestParam(required = true, name = "id") UUID id) {
        activityService.restore(id);
        return "redirect:/user/list/all";
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
    public String more(@RequestParam(required = false) UUID id,
                       @RequestParam(required = false) String lang,
                       Model model) {
        model.addAttribute("activity", activityService.getActivity(id));
        return "more";
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/add")
    public String add(Model model) {
        Activity activity = new Activity();
        model.addAttribute("activity", activity);
        return "add";
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping(path = "/add")
    public String newActivity(@Valid @ModelAttribute Activity activity,
                              BindingResult result,
                              Model model,
                              @AuthenticationPrincipal UserPrincipal userPrincipal) {
        LOGGER.info(activity);
        if (result.hasErrors()) {
            LOGGER.info(result);
            return "add";
        } else {
            activity.setUser(userPrincipal.getUser());
            activityService.addActivity(activity);
            model.addAttribute("activity", activity);
            return "new";
        }
    }
}
