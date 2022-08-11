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
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.result.view.RedirectView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
    public String list(
            @RequestParam(required = false) boolean save,
            @RequestParam(required = false) boolean error,
            @RequestParam(required = false) Optional<String> sort,
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            Model model) {
        LOGGER.info("sort: {}", sort);
        Sort sortParam;
        switch (sort.orElse("")) {
            case "DISTANCE":
                sortParam = Sort.by(Sort.Direction.DESC, "distanceOf");
                break;
            case "DURATION":
                sortParam = Sort.by(Sort.Direction.DESC, "duration");
                break;
            case "OWNER":
                sortParam = Sort.by(Sort.Direction.DESC, "userEntity.firstName");
                break;
            case "DELETED":
                sortParam = Sort.by(Sort.Direction.DESC, "isDeleted");
                break;
            case "SPORT":
                sortParam = Sort.by(Sort.Direction.DESC, "sport");
                break;
            default:
                sortParam = Sort.by(Sort.Direction.DESC, "dateTime");
                break;
        }

//        LOGGER.info(listParam);
        activityService.setActivityList();

        List<Activity> list = activityService.getAll(userPrincipal, sortParam);
        model.addAttribute("activities", list);
        model.addAttribute("listParam", null);
        model.addAttribute("save", save);
        model.addAttribute("error", error);
        model.addAttribute("all", false);
        return "list";
    }

    @GetMapping(path = "/list/all")
    @Secured("ROLE_ADMIN")
    public String listAll(Model model,
                          @RequestParam("page") Optional<Integer> page,
                          @RequestParam("size") Optional<Integer> size,
                          @RequestParam(required = false) Optional<String> sort
    ) {
        Sort sortParam;
        switch (sort.orElse("")) {
            case "DISTANCE":
                sortParam = Sort.by(Sort.Direction.DESC, "distanceOf");
                break;
            case "DURATION":
                sortParam = Sort.by(Sort.Direction.DESC, "duration");
                break;
            case "OWNER":
                sortParam = Sort.by(Sort.Direction.DESC, "userEntity.firstName");
                break;
            case "DELETED":
                sortParam = Sort.by(Sort.Direction.DESC, "isDeleted");
                break;
            case "SPORT":
                sortParam = Sort.by(Sort.Direction.DESC, "sport");
                break;
            default:
                sortParam = Sort.by(Sort.Direction.DESC, "dateTime");
                break;
        }
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Activity> activityPage = activityService.getPage(PageRequest.of(currentPage - 1, pageSize), sortParam);
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
        model.addAttribute("all", true);
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
