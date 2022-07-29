package com.jaworski.sportdiary.controller;

import com.jaworski.sportdiary.domain.Activity;
import com.jaworski.sportdiary.domain.User;
import com.jaworski.sportdiary.entity.ActivityEntity;
import com.jaworski.sportdiary.entity.UserEntity;
import com.jaworski.sportdiary.repository.UserEntityRepository;
import com.jaworski.sportdiary.mapper.ActivityMapper;
import com.jaworski.sportdiary.service.activity.ActivityService;
import com.jaworski.sportdiary.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private static final Logger LOGGER = LogManager.getLogger(IndexController.class);
    private final ActivityService activityService;
    private final ActivityMapper activityMapper;
    private final UserEntityRepository userEntityRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;


    @GetMapping(path = {"/", "welcome", "index"})
    public String index(Model model) {
        List<Activity> list = activityService.getActivityList();
        model.addAttribute("activities", list);
        return "welcome";
    }

    @GetMapping(path = "/login")
    public String login(Model model,
                        @RequestParam(required = false, name = "registration", defaultValue = "false") boolean registration) {
        System.out.println("reg: " + registration);
        model.addAttribute("registration", registration);
        return "login";
    }

    @GetMapping(path = "/signup")
    public String signup(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "signup";
    }

    @PostMapping("/signup")
    public RedirectView signup(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model) {
        LOGGER.info("Signup: " + user);
        if (bindingResult.hasErrors()) {
            LOGGER.info("Signup: " + bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage()).reduce("", (a, b) -> a + ", " + b));
            return new RedirectView("/signup");
        } else {
            userService.addUser(user);
//            UserEntity userEntity = new UserEntity(user.getFirstName(), passwordEncoder.encode(user.getPassword()), "USER", "");
//            userEntity.setEmail(user.getEmail());
//            userEntityRepository.save(userEntity);
            return new RedirectView("/login?registration=true");
        }
    }

    @GetMapping("/test")
    public String test(Model model) {
        model.addAttribute("User", new User());
        return "test";
    }

    @PostMapping("/test")
    public String addUser(@ModelAttribute User user) {
        System.out.println(user);
        return "redirect:/test";
    }

    @GetMapping("/add")
    public String addActivity(Model model) {
        model.addAttribute("activity", new Activity());
        return "add_activity";
    }

    @PostMapping("/add")
    public String add_Activity(@ModelAttribute Activity activity) {
        System.out.println(activity);
        ActivityEntity activityEntity = activityMapper.ActivityToEntity(activity);
        System.out.println(activityEntity);
        return "redirect:/add";
    }

    @GetMapping("/403")
    public String error403() {
        return "403";
    }
}
