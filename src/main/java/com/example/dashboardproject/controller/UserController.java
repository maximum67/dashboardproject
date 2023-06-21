package com.example.dashboardproject.controller;

import com.example.dashboardproject.models.Role;
import com.example.dashboardproject.models.User;
import com.example.dashboardproject.services.DashboardParamService;
import com.example.dashboardproject.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/userControl")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final DashboardParamService dashboardParamService;

    @PostMapping("/updatePassword/{id}")
    public String updatePassword(@RequestParam("userId") User user, @RequestParam("password") String password){
        userService.updatePassword(user, password);
        return "redirect:/userControl/userEdit/"+user.getId();
    }
    @GetMapping("/userList")
    public String getUserList(Model model){
        model.addAttribute("title","Пользователи");
        model.addAttribute("users", userService.list());
        model.addAttribute("params",dashboardParamService.list());
        return "userList";
    }
    @GetMapping("/userEdit/{user}")
    public  String userEdit(@PathVariable("user") User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        model.addAttribute("params",dashboardParamService.list());
        return "userEdit";
    }
    @PostMapping("/user/ban/{id}")
    public String userBan(@PathVariable("id") Long id) {
        userService.banUser(id);
        return "redirect:/userControl/userEdit/"+id;
    }
    @PostMapping("/user/changRoles/{id}")
    public String changeUserRoles(@RequestParam("userId") User user, @RequestParam Map<String, String> form){
        userService.changeUserRoles(user, form);
        return "redirect:/userControl/userEdit/"+user.getId();
    }
    @PostMapping("/user/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return "redirect:/userControl/userList";
    }
}
