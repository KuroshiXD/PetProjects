package com.test_task.springproject.controllers;

import com.test_task.springproject.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String ShowLoginForm() {
        return "login";
    }

    @PostMapping("/custom-redirect")
    public String LoginUser(@RequestParam String username, @RequestParam String password) {
        userService.loginUser(username, password);
        return "redirect:/welcome/" + username;
    }
}
