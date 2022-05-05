package com.jaworski.sportdiary.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    static final Logger logger = LogManager.getLogger(AdminController.class);

    @GetMapping("")
    public String main() {
        return "admin";
    }
}
