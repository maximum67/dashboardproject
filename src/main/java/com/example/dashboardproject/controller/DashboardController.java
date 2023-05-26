package com.example.dashboardproject.controller;


import com.example.dashboardproject.models.Role;
import com.example.dashboardproject.models.User;
import com.example.dashboardproject.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
@RequiredArgsConstructor
@RequestMapping("/dash/")
public class DashboardController {

    private final UserService userService;

    @GetMapping("/dashboardV1")
    public String getDashboard(Model model){
         model.addAttribute("title","Dashboard");
         return "dashboardV1";
     }

}

