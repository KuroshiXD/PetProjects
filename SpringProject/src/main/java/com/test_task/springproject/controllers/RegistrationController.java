package com.test_task.springproject.controllers;

import com.test_task.springproject.models.User;
import com.test_task.springproject.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping()
    public String registerUser(@RequestParam String username, @RequestParam String email,
                               @RequestParam String password) {
        User user = new User(username, password, email);
        userService.registerUser(user);
        return "redirect:/login";
    }
}
