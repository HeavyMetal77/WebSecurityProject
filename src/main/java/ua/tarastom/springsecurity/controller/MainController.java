package ua.tarastom.springsecurity.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequestMapping
public class MainController {

    @GetMapping("/")
    public String showHome() {
        return "home";
    }
}
