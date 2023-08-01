package com.example.dashboardproject.controller;

import com.example.dashboardproject.models.*;
import com.example.dashboardproject.services.*;
import lombok.RequiredArgsConstructor;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;



@Controller
@RequiredArgsConstructor
@RequestMapping("/dash/")
public class DashboardController {

    private final V1service v1service;
    private final UserService userService;
    private final DashboardParamService dashboardParamService;
    private final DashboardPeriodService dashboardPeriodService;
    private final DashboardTypeLineService dashboardTypeLineService;
    private final HiddenSettingService hiddenSettingService;
    private final GroupParamService groupParamService;
    private final GroupPeriodService groupPeriodService;
    private final GroupTypeLineService groupTypeLineService;
    private final HiddenSettingGroupService hiddenSettingGroupService;
    Base64.Decoder decoder = Base64.getDecoder();
    Base64.Encoder encoder = Base64.getEncoder();

    @GetMapping("/dashboardV1")
    public String getDashboard(Model model) {
        model.addAttribute("title", "Dashboard");
        model.addAttribute("params", dashboardParamService.list());
        model.addAttribute("paramList", dashboardParamService.findAllByHidden());
        model.addAttribute("isAdmin", userService.getUserByPrincipal().isAdmin());
        model.addAttribute("groupParams", groupParamService.list());
        model.addAttribute("groupParamList", groupParamService.findAllByHidden());
        if (userService.getUserByPrincipal().isAdmin() || userService.getUserByPrincipal().isUser()) {
            return "dashboardV1";
        } else {
            return "dashboardDemo";
        }
    }
    @GetMapping("/groupdashboardV1")
    public String getGroupDashboard(Model model) {
        model.addAttribute("title", "Groupdashboard");
        model.addAttribute("params", dashboardParamService.list());
        model.addAttribute("paramList", dashboardParamService.findAllByHidden());
        model.addAttribute("isAdmin", userService.getUserByPrincipal().isAdmin());
        model.addAttribute("groupParams", groupParamService.list());
        model.addAttribute("groupParamList", groupParamService.findAllByHidden());
        if (userService.getUserByPrincipal().isAdmin() || userService.getUserByPrincipal().isUser()) {
            return "groupdashboardV1";
        } else {
            return "dashboardDemo";
        }
    }

    @GetMapping("/dashboard")
    public String getDashboardTest(Model model) {
        model.addAttribute("title", "Dashboard");
        model.addAttribute("params", dashboardParamService.list());
        model.addAttribute("groupParams", groupParamService.list());
        model.addAttribute("isAdmin", userService.getUserByPrincipal().isAdmin());
        model.addAttribute("period_select", dashboardPeriodService.getPeriodByUser());
        model.addAttribute("typeline_select", dashboardTypeLineService.getTypeLineByUser());
        model.addAttribute("groupParamList", groupParamService.findAllByHidden());
        if (userService.getUserByPrincipal().isAdmin() || userService.getUserByPrincipal().isUser()) {
            return "dashboard";
        } else {
            return "dashboardDemo";
        }
    }

    @GetMapping("/groupdashboard")
    public String getGroupDashboardTest(Model model) {
        model.addAttribute("title", "GroupDashboard");
        model.addAttribute("params", dashboardParamService.list());
        model.addAttribute("groupParams", groupParamService.list());
        model.addAttribute("isAdmin", userService.getUserByPrincipal().isAdmin());
        model.addAttribute("period_select", groupPeriodService.getPeriodByUser());
        model.addAttribute("typeline_select", groupTypeLineService.getTypeLineByUser());
        model.addAttribute("groupParamList", groupParamService.findAllByHidden());
        if (userService.getUserByPrincipal().isAdmin() || userService.getUserByPrincipal().isUser()) {
            return "groupdashboard";
        } else {
            return "dashboardDemo";
        }
    }


    @GetMapping("/groupdashboard/{groupParam}")
    public String getGroupDashboard(@PathVariable ("groupParam") GroupParam groupParam, Model model) {
        model.addAttribute("title", "groupdashboard");
        model.addAttribute("params", dashboardParamService.list());
        model.addAttribute("groupParams", groupParamService.list());
        model.addAttribute("groupParamId", groupParam.getId());
        model.addAttribute("groupParamName", groupParam.getGroupName());
        model.addAttribute("groupParamList", groupParamService.findAllByHidden());
        model.addAttribute("isAdmin", userService.getUserByPrincipal().isAdmin());
        String str = "";
        Long periodValue = 7L;
        switch (groupPeriodService.getPeriodByUserAndParam(groupParam).getDashboardPeriod()) {
            case PERIOD_MONTH:
                str = "Период месяц";
                periodValue = 30L;
                break;
            case PERIOD_YEAR:
                str = "Период год";
                periodValue = 365L;
                break;
            case PERIOD_QUARTER:
                str = "Период квартал";
                periodValue = 90L;
                break;
            default:
                str = "Период неделя";
        };
        String str2 = "";
        switch (groupTypeLineService.getTypeLineByUserAndParam(groupParam).getTypeLine()) {
            case BAR -> str2 = "Гистограмма";
            case BAR3D -> str2 = "Гистограмма 3D";
            case TEXT -> str2 = "Текст";
            case PIE -> str2 = "Круг";
            case DONUT3D-> str2 = "Кольцо 3D";
            case LINE_AREA -> str2 = "Область";
            case LINE_REGRESS -> str2 = "Область с регрессом";
            default -> str2 = "Линия";
        };
        model.addAttribute("period_select", str);
        model.addAttribute("typeline_select", str2);
        model.addAttribute("periodValue", periodValue);
//        model.addAttribute("parametrTable", v1service.getParametrTable(groupParam.getDashboardParams().get(4), Integer.parseInt(String.valueOf(periodValue))));
        model.addAttribute("isHidden", hiddenSettingGroupService.getHiddenSettingByUserAndParam(groupParam).getIsHidden());
        if (userService.getUserByPrincipal().isAdmin() || userService.getUserByPrincipal().isUser()) {
            return "groupdashboard";
        } else {
            return "dashboardDemo";
        }
    }

    @GetMapping("/dashboard/{dashboardParam}")
    public String getDashboardV(@PathVariable("dashboardParam") DashboardParam dashboardParam, Model model) {
        model.addAttribute("title", "Dashboard");
        model.addAttribute("params", dashboardParamService.list());
        model.addAttribute("groupParams", groupParamService.list());
        model.addAttribute("dashboardParam", dashboardParam.getName());
        model.addAttribute("dashboardParamId", dashboardParam.getId());
        model.addAttribute("groupParamList", groupParamService.findAllByHidden());
        model.addAttribute("isAdmin", userService.getUserByPrincipal().isAdmin());
        String str = "";
        Long periodValue = 7L;
        switch (dashboardPeriodService.getPeriodByUserAndParam(dashboardParam).getDashboardPeriod()) {
            case PERIOD_MONTH:
                str = "Период месяц";
                periodValue = 30L;
                break;
            case PERIOD_YEAR:
                str = "Период год";
                periodValue = 365L;
                break;
            case PERIOD_QUARTER:
                str = "Период квартал";
                periodValue = 90L;
                break;
            default:
                str = "Период неделя";
        };
        String str2 = "";
        switch (dashboardTypeLineService.getTypeLineByUserAndParam(dashboardParam).getTypeLine()) {
            case BAR -> str2 = "Гистограмма";
            case BAR3D -> str2 = "Гистограмма 3D";
            case TEXT -> str2 = "Текст";
            case PIE -> str2 = "Круг";
            case DONUT3D-> str2 = "Кольцо 3D";
            case LINE_AREA -> str2 = "Область";
            case LINE_REGRESS -> str2 = "Область с регрессом";
            default -> str2 = "Линия";
        };
        model.addAttribute("period_select", str);
        model.addAttribute("typeline_select", str2);
        model.addAttribute("periodValue", periodValue);
        model.addAttribute("parametrTable", v1service.getParametrTable(dashboardParam, Integer.parseInt(String.valueOf(periodValue))));
        model.addAttribute("isHidden", hiddenSettingService.getHiddenSettingByUserAndParam(dashboardParam).getIsHidden());
        if (userService.getUserByPrincipal().isAdmin() || userService.getUserByPrincipal().isUser()) {
            return "dashboard";
        } else {
            return "dashboardDemo";
        }
    }
    @GetMapping("/updateHiddenDashboardParam/{dashboardParam}/{n}")
    public String updateHiddenDashboardParam(@PathVariable ("dashboardParam") DashboardParam dashboardParam,
                                             @PathVariable ("n") Long n){
        if (n==1) {
            hiddenSettingService.getHiddenSettingByUserAndParam(dashboardParam).setIsHidden(true);
            HiddenSetting hiddenSetting1 = hiddenSettingService.getHiddenSettingByUserAndParam(dashboardParam);
            hiddenSettingService.updateHiddenSetting(hiddenSetting1);
        }else {
            hiddenSettingService.getHiddenSettingByUserAndParam(dashboardParam).setIsHidden(false);
            HiddenSetting hiddenSetting2 = hiddenSettingService.getHiddenSettingByUserAndParam(dashboardParam);
            hiddenSettingService.updateHiddenSetting(hiddenSetting2);
        }
        if (userService.getUserByPrincipal().isAdmin() || userService.getUserByPrincipal().isUser()) {
            return "redirect:/dash/dashboard/"+dashboardParam.getId();
        } else {
            return "dashboardDemo";
        }
    }

    @GetMapping("/updateHiddenGroupParam/{groupParam}/{n}")
    public String updateHiddenGroupParam(@PathVariable ("groupParam") GroupParam groupParam,
                                             @PathVariable ("n") Long n){
        if (n==1) {
            hiddenSettingGroupService.getHiddenSettingByUserAndParam(groupParam).setIsHidden(true);
            HiddenSettingGroup hiddenSettingGroup = hiddenSettingGroupService.getHiddenSettingByUserAndParam(groupParam);
            hiddenSettingGroupService.updateHiddenSettingGroup(hiddenSettingGroup);
        }else {
            hiddenSettingGroupService.getHiddenSettingByUserAndParam(groupParam).setIsHidden(false);
            HiddenSettingGroup hiddenSettingGroup = hiddenSettingGroupService.getHiddenSettingByUserAndParam(groupParam);
            hiddenSettingGroupService.updateHiddenSettingGroup(hiddenSettingGroup);
        }
        if (userService.getUserByPrincipal().isAdmin() || userService.getUserByPrincipal().isUser()) {
            return "redirect:/dash/groupdashboard/"+groupParam.getId();
        } else {
            return "dashboardDemo";
        }
    }
    //______________________________________________________________________________________________________________

    //___________________________________________________________________________________________________________
    @GetMapping("/dashboardparamList")
    public String getDashboardParamList(Model model) {
        model.addAttribute("title", "dashboardparamlist");
        model.addAttribute("dashboardParams", dashboardParamService.list());
        model.addAttribute("params", dashboardParamService.list());
        model.addAttribute("groupParams", groupParamService.list());
        model.addAttribute("isAdmin", userService.getUserByPrincipal().isAdmin());
        if (userService.getUserByPrincipal().isAdmin() || userService.getUserByPrincipal().isUser()) {
            return "dashboardparamList";
        } else {
            return "dashboardDemo";
        }
    }

    @GetMapping("/dashboardparamEdit/{dashboardparam}")
    public String getDashboardparamEdit(@PathVariable("dashboardparam") DashboardParam dashboardParam, Model model) {
        model.addAttribute("title", "dashboardparam");
        model.addAttribute("dashboardParam", dashboardParam);
        model.addAttribute("params", dashboardParamService.list());
        model.addAttribute("groupParams", groupParamService.list());
        model.addAttribute("isAdmin", userService.getUserByPrincipal().isAdmin());
        if (userService.getUserByPrincipal().isAdmin() || userService.getUserByPrincipal().isUser()) {
            return "dashboardparamEdit";
        } else {
            return "dashboardDemo";
        }
    }

    @PostMapping("/updateDashboardparam/{dashboardparam}")
    public String updateDashboardParam(@RequestParam("name") String name,
                                      @RequestParam("icon") String icon,
                                      @PathVariable("dashboardparam") DashboardParam dashboardParam) {
        dashboardParam.setName(name);
        dashboardParam.setIcon(icon);
        dashboardParamService.updateDashboardParam(dashboardParam);
        if (userService.getUserByPrincipal().isAdmin() || userService.getUserByPrincipal().isUser()) {
            return "redirect:/dash/dashboardparamList";
        } else {
            return "dashboardDemo";
        }
    }

    @PostMapping("/createNewDashboardparam")
    public String createNewDashboardparam(@RequestParam("name") String name,
                                          @RequestParam("icon") String icon) {
        DashboardParam dashboardParam = new DashboardParam();
        dashboardParam.setName(name);
        dashboardParam.setIcon(icon);
        dashboardParamService.updateDashboardParam(dashboardParam);
        if (userService.getUserByPrincipal().isAdmin() || userService.getUserByPrincipal().isUser()) {
            return "redirect:/dash/dashboardparamList";
        } else {
            return "dashboardDemo";
        }
    }

    @GetMapping("/dashboardparamNew")
    public String dashboardparamNew(Model model) {
        model.addAttribute("title", "new dashboardparam");
        model.addAttribute("params", dashboardParamService.list());
        model.addAttribute("groupParams", groupParamService.list());
        model.addAttribute("isAdmin", userService.getUserByPrincipal().isAdmin());
        if (userService.getUserByPrincipal().isAdmin() || userService.getUserByPrincipal().isUser()) {
            return "dashboardparamNew";
        } else {
            return "dashboardDemo";
        }
    }


    @PostMapping("/deleteDashboardparam/{dashboardparam}")
    public String deleteDashboardparam(@PathVariable("dashboardparam") DashboardParam dashboardParam) {
        dashboardParamService.deleteDashboardParam(dashboardParam.getId());
        if (userService.getUserByPrincipal().isAdmin() || userService.getUserByPrincipal().isUser()) {
            return "redirect:/dash/dashboardparamList";
        } else {
            return "dashboardDemo";
        }
    }
    //___________________________________________________________________________________________________

    @GetMapping("/dashboardV1/{p}/{dashboardParamId}")
    public String getPeriodSettingV1(@PathVariable("p") Long p, @PathVariable("dashboardParamId") Long id) {
        DashboardParam dashboardParam = dashboardParamService.getById(id);
        if (dashboardParam.getId() == 1000000000L) return "redirect:/dash/dashboardV1";
        PeriodSetting periodSetting1 = dashboardPeriodService.getPeriodByUserAndParam(dashboardParam);
        if (p == 1L) {
            periodSetting1.setDashboardPeriod(DashboardPeriod.PERIOD_WEEK);
        } else if (p == 2L) {
            periodSetting1.setDashboardPeriod(DashboardPeriod.PERIOD_MONTH);
        } else if (p == 3L) {
            periodSetting1.setDashboardPeriod(DashboardPeriod.PERIOD_QUARTER);
        } else if (p == 4L) {
            periodSetting1.setDashboardPeriod(DashboardPeriod.PERIOD_YEAR);
        } else {
            periodSetting1.setDashboardPeriod(DashboardPeriod.PERIOD_WEEK);
        }
        dashboardPeriodService.updateDashboardPeriod(periodSetting1);
        if (userService.getUserByPrincipal().isAdmin() || userService.getUserByPrincipal().isUser()) {
            return "redirect:/dash/dashboardV1";
        } else {
            return "dashboardDemo";
        }
    }


    @GetMapping("/dashboard/{p}/{dashboardParamId}")
    public String getPeriodSetting(@PathVariable("p") Long p, @PathVariable("dashboardParamId") Long id) {
        DashboardParam dashboardParam = dashboardParamService.getById(id);
        if (dashboardParam.getId() == 1000000000L) return "redirect:/dash/dashboard";
        PeriodSetting periodSetting = dashboardPeriodService.getPeriodByUserAndParam(dashboardParam);
        if (p == 1L) {
            periodSetting.setDashboardPeriod(DashboardPeriod.PERIOD_WEEK);
        } else if (p == 2L) {
            periodSetting.setDashboardPeriod(DashboardPeriod.PERIOD_MONTH);
        } else if (p == 3L) {
            periodSetting.setDashboardPeriod(DashboardPeriod.PERIOD_QUARTER);
        } else if (p == 4L) {
            periodSetting.setDashboardPeriod(DashboardPeriod.PERIOD_YEAR);
        } else {
            periodSetting.setDashboardPeriod(DashboardPeriod.PERIOD_WEEK);
        }
        dashboardPeriodService.updateDashboardPeriod(periodSetting);
        if (userService.getUserByPrincipal().isAdmin() || userService.getUserByPrincipal().isUser()) {
            return "redirect:/dash/dashboard/" + dashboardParam.getId();
        } else {
            return "dashboardDemo";
        }
    }

    @GetMapping("/groupdashboard/{p}/{groupParamId}")
    public String getPeriodSettingGroup(@PathVariable("p") Long p, @PathVariable("groupParamId") Long id) {
        GroupParam groupParam = groupParamService.getById(id);
        if (groupParam.getId() == 1000000000L) return "redirect:/dash/groupdashboard";
        PeriodSettingGroup periodSettingGroup = groupPeriodService.getPeriodByUserAndParam(groupParam);
        if (p == 1L) {
            periodSettingGroup.setDashboardPeriod(DashboardPeriod.PERIOD_WEEK);
        } else if (p == 2L) {
            periodSettingGroup.setDashboardPeriod(DashboardPeriod.PERIOD_MONTH);
        } else if (p == 3L) {
            periodSettingGroup.setDashboardPeriod(DashboardPeriod.PERIOD_QUARTER);
        } else if (p == 4L) {
            periodSettingGroup.setDashboardPeriod(DashboardPeriod.PERIOD_YEAR);
        } else {
            periodSettingGroup.setDashboardPeriod(DashboardPeriod.PERIOD_WEEK);
        }
        groupPeriodService.updateGroupPeriod(periodSettingGroup);
        if (userService.getUserByPrincipal().isAdmin() || userService.getUserByPrincipal().isUser()) {
            return "redirect:/dash/groupdashboard/" + groupParam.getId();
        } else {
            return "dashboardDemo";
        }
    }

    @GetMapping("/typeLine/{p}/{dashboardParamId}")
    public String getLineSetting(@PathVariable("p") Long p, @PathVariable("dashboardParamId") Long id) {
        DashboardParam dashboardParam = dashboardParamService.getById(id);
        if (dashboardParam.getId() == 1000000000L) return "redirect:/dash/dashboard";
        LineSetting lineSetting = dashboardTypeLineService.getTypeLineByUserAndParam(dashboardParam);
        if (p == 3L) {
            lineSetting.setTypeLine(TypeLine.BAR3D);
        } else if (p == 6L) {
            lineSetting.setTypeLine(TypeLine.TEXT);
        } else if (p == 2L) {
            lineSetting.setTypeLine(TypeLine.BAR);
        } else if (p == 4L) {
            lineSetting.setTypeLine(TypeLine.LINE_AREA);
        } else if (p == 5L) {
            lineSetting.setTypeLine(TypeLine.LINE_REGRESS);
        } else if (p == 7L) {
            lineSetting.setTypeLine(TypeLine.PIE);
        } else if (p == 8L) {
            lineSetting.setTypeLine(TypeLine.DONUT3D);
        }else {
            lineSetting.setTypeLine(TypeLine.LINE);
        }
        dashboardTypeLineService.updateDashboardTypeline(lineSetting);
        if (userService.getUserByPrincipal().isAdmin() || userService.getUserByPrincipal().isUser()) {
            return "redirect:/dash/dashboard/" + dashboardParam.getId();
        } else {
            return "dashboardDemo";
        }
    }

    @GetMapping("/grouptypeLine/{p}/{groupParamId}")
    public String getLineSettingGroup(@PathVariable("p") Long p, @PathVariable("groupParamId") Long id) {
        GroupParam groupParam = groupParamService.getById(id);
        if (groupParam.getId() == 1000000000L) return "redirect:/dash/groupdashboard";
        LineSettingGroup lineSettingGroup = groupTypeLineService.getTypeLineByUserAndParam(groupParam);
        if (p == 3L) {
            lineSettingGroup.setTypeLine(TypeLine.BAR3D);
        } else if (p == 6L) {
            lineSettingGroup.setTypeLine(TypeLine.TEXT);
        } else if (p == 2L) {
            lineSettingGroup.setTypeLine(TypeLine.BAR);
        } else if (p == 4L) {
            lineSettingGroup.setTypeLine(TypeLine.LINE_AREA);
        } else if (p == 5L) {
            lineSettingGroup.setTypeLine(TypeLine.LINE_REGRESS);
        } else if (p == 7L) {
            lineSettingGroup.setTypeLine(TypeLine.PIE);
        } else if (p == 8L) {
                lineSettingGroup.setTypeLine(TypeLine.DONUT3D);
        } else {
            lineSettingGroup.setTypeLine(TypeLine.LINE);
        }
        groupTypeLineService.updateGroupTypeline(lineSettingGroup);
        if (userService.getUserByPrincipal().isAdmin() || userService.getUserByPrincipal().isUser()) {
            return "redirect:/dash/groupdashboard/" + groupParam.getId();
        } else {
            return "dashboardDemo";
        }
    }
}

