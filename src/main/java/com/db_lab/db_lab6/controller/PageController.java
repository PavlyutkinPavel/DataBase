package com.db_lab.db_lab6.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/home")
    public String home() {
        return "static/pages/home";
    }

    @GetMapping("/registration")
    public String registration() {
        return "static/pages/registration";
    }

    @GetMapping("/login")
    public String login() {
        return "static/pages/login";
    }
}
