package com.example.dashboardproject.controller;


import com.example.dashboardproject.models.Role;
import com.example.dashboardproject.models.User;
import com.example.dashboardproject.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;



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
    @GetMapping("/userList")
    public String getUserList(Model model){
        model.addAttribute("title","Пользователи");
        model.addAttribute("users", userService.list());
        System.out.println(userService.list().get(1).getRoles());
        return "userList";
    }
    @GetMapping("/userEdit/{user}")
    public  String userEdit(@PathVariable ("user") User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }
}
