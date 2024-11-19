package com.test_task.springproject.controllers;

import com.test_task.springproject.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/welcome")
public class WelcomeController {

    private final UserService userService;

    public WelcomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public String CreateWelcome(@PathVariable String username, Model model) {
        model.addAttribute("username", username);
        return "welcome";
    }
}
