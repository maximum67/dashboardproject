package com.example.dashboardproject.controller;

import com.example.dashboardproject.models.User;
import com.example.dashboardproject.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("title", "Авторизация");
        return "login";
    }

    @GetMapping("/home")
    public String getHomePage(Model model) {
        model.addAttribute("title", "home");
        return "home";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("errorMessage", true);
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(User user, Model model) {
        if (!userService.createUser(user)) {
            model.addAttribute("errorMessage", false);
            return "registration";
        }
        userService.createUser(user);
        return "redirect:/auth/login";
    }

}


