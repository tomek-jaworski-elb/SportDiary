package com.jaworski.sportdiary.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/public")
public class PublicController {

    private static final Logger logger = LogManager.getLogger(PublicController.class);

    @GetMapping("")
    public String main() {
        return "public";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin")
    public String mainAdmin() {
        return "admin";
    }

    @GetMapping("/test")
    public String test() {
        return "list";
    }
}
