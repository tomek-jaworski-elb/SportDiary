package com.jaworski.sportdiary.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/public")
public class PublicController {

    @GetMapping("")
    public String main() {
        return "public";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin")
    public String mainAdmin() {
        return "admin";
    }
}
