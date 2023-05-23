package com.example.dashboardproject.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/dash/")
public class DashboardController {

     @GetMapping("/dashboardV1")
    public String getDashboard(Model model){
         model.addAttribute("title","Dashboard");
         return "dashboardV1";
     }

}
