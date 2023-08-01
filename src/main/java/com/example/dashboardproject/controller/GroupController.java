package com.example.dashboardproject.controller;

import com.example.dashboardproject.models.GroupParam;
import com.example.dashboardproject.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequestMapping("/group/")
public class GroupController {

    private final V1service v1service;
    private final UserService userService;
    private final DashboardParamService dashboardParamService;
    private final DashboardPeriodService dashboardPeriodService;
    private final DashboardTypeLineService dashboardTypeLineService;
    private final HiddenSettingService hiddenSettingService;
    private final GroupParamService groupParamService;

    @PostMapping("/createNewGroupParam")
    public String createNewGroupParam(@RequestParam("groupName") String groupName,
                                      @RequestParam Map<String,String> mapParams,
                                      @ModelAttribute("groupParam") GroupParam groupParam,
                                      Model model){
        groupParamService.changeGroupParam(groupParam, mapParams, dashboardParamService.list());
        groupParamService.updateGroupParam(groupParam);
        model.addAttribute("title", "dashboardparamlist");
        model.addAttribute("dashboardParams", dashboardParamService.list());
        model.addAttribute("params", dashboardParamService.list());
        model.addAttribute("groupParams", groupParamService.list());
        model.addAttribute("isAdmin", userService.getUserByPrincipal().isAdmin());
        return "redirect:/dash/dashboardparamList";
    }

    @PostMapping("/updateGroupParam/{groupParam}")
    public String updateDashboardParam(@RequestParam("groupName") String groupName,
                                       @RequestParam Map<String,String> mapParams,
                                       @PathVariable("groupParam") GroupParam groupParam) {
        groupParam.setGroupName(groupName);
        groupParamService.changeGroupParam(groupParam, mapParams, dashboardParamService.list());
        groupParamService.updateGroupParam(groupParam);
        return "redirect:/dash/dashboardparamList";
    }

    @PostMapping("/deleteGroupParam/{groupParam}")
    public String deleteGroupParam(@PathVariable("groupParam") GroupParam groupParam) {
        groupParamService.deleteGroupParam(groupParam.getId());
        return "redirect:/dash/dashboardparamList";
    }

    @GetMapping("/groupParamEdit/{groupParam}")
    public String groupparamEdit(@PathVariable("groupParam") GroupParam groupParam, Model model) {
        model.addAttribute("title", "groupParamEdit");
        model.addAttribute("groupParam", groupParamService.getById(groupParam.getId()));
        model.addAttribute("groupDashboardParams", groupParamService.getNameParamsOfGroup(groupParam));
        model.addAttribute("params", dashboardParamService.list());
        model.addAttribute("groupParams", groupParamService.list());
        model.addAttribute("isAdmin", userService.getUserByPrincipal().isAdmin());
        return "groupParamEdit";
    }

    @GetMapping("/groupParamNew")
    public String groupparamNew(Model model) {
        model.addAttribute("title", "new_group-param");
        model.addAttribute("params", dashboardParamService.list());
        model.addAttribute("groupParams", groupParamService.list());
        model.addAttribute("isAdmin", userService.getUserByPrincipal().isAdmin());
        model.addAttribute("groupParam", new GroupParam());
        return "groupParamNew";
    }


}
