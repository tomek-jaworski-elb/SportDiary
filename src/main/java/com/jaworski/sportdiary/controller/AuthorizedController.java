package com.jaworski.sportdiary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class AuthorizedController {

    @GetMapping("")
    public String main() {
        return "user";
    }
}
