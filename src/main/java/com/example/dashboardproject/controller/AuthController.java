package com.example.dashboardproject.controller;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("title","Авторизация");
        return "login";
    }
    @GetMapping("/home")
    public String getHomePage(Model model){
        model.addAttribute("title","home");
        return "home";
    }

}

