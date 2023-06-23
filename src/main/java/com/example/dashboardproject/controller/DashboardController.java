package com.example.dashboardproject.controller;



import com.example.dashboardproject.models.*;
import com.example.dashboardproject.repositories.DashboardParamRepository;
import com.example.dashboardproject.repositories.FtpSettingRepository;
import com.example.dashboardproject.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequiredArgsConstructor
@RequestMapping("/dash/")
public class DashboardController {

    private final FtpService ftpService;
    private final V1service v1service;
    private final UserService userService;
    private final DashboardParamService dashboardParamService;
    private final DashboardPeriodService dashboardPeriodService;
    private final DashboardTypeLineService dashboardTypeLineService;
    Base64.Decoder decoder = Base64.getDecoder();
    Base64.Encoder encoder = Base64.getEncoder();

    @GetMapping("/dashboardV1")
    public String getDashboard(Model model) {
        model.addAttribute("title", "Dashboard");
        model.addAttribute("params",dashboardParamService.list());
        model.addAttribute("isAdmin", userService.getUserByPrincipal().isAdmin());
//        model.addAttribute("dashboardParamIds",dashboardParamService.getDashboardParamIds());
//        model.addAttribute("dashboardParam", dashboardParamService.getById(1L).getName());
//        model.addAttribute("dashboardParamId", dashboardParamService.getById(1L).getId());
//        model.addAttribute("period_select", dashboardPeriodService.getPeriodByUser());
        if (userService.getUserByPrincipal().isAdmin() || userService.getUserByPrincipal().isUser()){
            return "dashboardV1";
        }else {
            return "dashboardDemo";
        }

    }
    @GetMapping("/dashboard")
        public String getDashboardTest(Model model) {
        model.addAttribute("title","Dashboard");
        model.addAttribute("params",dashboardParamService.list());
        model.addAttribute("isAdmin", userService.getUserByPrincipal().isAdmin());
//        model.addAttribute("dashboardParam", dashboardParamService.getById(1L).getName());
//        model.addAttribute("dashboardParamId", dashboardParamService.getById(1L).getId());
        model.addAttribute("period_select", dashboardPeriodService.getPeriodByUser());
        model.addAttribute("typeline_select",dashboardTypeLineService.getTypeLineByUser());
            return "dashboard";
   }
    @GetMapping("/dashboard/{dashboardParam}")
    public String getDashboardV(@PathVariable("dashboardParam") DashboardParam dashboardParam,Model model) {
        model.addAttribute("title", "Dashboard");
        model.addAttribute("params", dashboardParamService.list());
        model.addAttribute("dashboardParam", dashboardParam.getName());
        model.addAttribute("dashboardParamId",dashboardParam.getId());
        model.addAttribute("isAdmin", userService.getUserByPrincipal().isAdmin());
        String str = "";
        Long periodValue = 7L;
        switch (dashboardPeriodService.getPeriodByUserAndParam(dashboardParam).getDashboardPeriod()) {
            case PERIOD_MONTH : str = "Период месяц";
            periodValue=30L;
            break;
            case PERIOD_YEAR : str = "Период год";
                periodValue=365L;
            break;
            case PERIOD_QUARTER : str = "Период квартал";
                periodValue=90L;
            break;
            default : str = "Период неделя";
        };
        String str2 = "";
        switch (dashboardTypeLineService.getTypeLineByUserAndParam(dashboardParam).getTypeLine()){
            case BAR -> str2 = "Гистограмма";
            case LINE_AREA -> str2 = "Область";
            case LINE_REGRESS -> str2 = "Область с регрессом";
            default -> str2 = "Линия";
        };
        model.addAttribute("period_select", str);
        model.addAttribute("typeline_select", str2);
        model.addAttribute("periodValue", periodValue);
        model.addAttribute("parametrTable", v1service.getParametrTable(dashboardParam,Integer.parseInt(String.valueOf(periodValue))));
        return "dashboard";
    }
    //_________________________________________________________________________________________________________

    @GetMapping("/ftpsettingList")
    public String getFtpSettingList(Model model) {
        model.addAttribute("title","ftpsettingList");
        model.addAttribute("settings",ftpService.list());
        model.addAttribute("params",dashboardParamService.list());
        model.addAttribute("isAdmin", userService.getUserByPrincipal().isAdmin());
        return "ftpsettingList";
    }
    @GetMapping("/ftpsettingEdit/{ftpsetting}")
    public String getFtpSettingEdit(@PathVariable ("ftpsetting") FtpSetting ftpSetting, Model model) {
        model.addAttribute("title","ftpsettingEdit");
        model.addAttribute("ftpsetting", ftpSetting);
        model.addAttribute("password", new String(decoder.decode(ftpSetting.getPassword())));
        model.addAttribute("dashboardparams", dashboardParamService.list());
        model.addAttribute("dashparam", ftpSetting.getDashboardParam());
        model.addAttribute("params",dashboardParamService.list());
        model.addAttribute("isAdmin", userService.getUserByPrincipal().isAdmin());
        return "ftpsettingEdit";
    }
    @PostMapping("/updateFtpsetting/{ftpsetting}")
    public String updateFtpSetting(@RequestParam ("login") String login, @RequestParam ("host") String host,
                                   @RequestParam ("port") String port, @RequestParam ("filename") String filename,
                                   @RequestParam ("password") String password, @RequestParam ("timetask") LocalTime timeTask,
                                   @RequestParam ("dashboardparam") DashboardParam dashboardParam,
                                   @RequestParam ("trDate") Integer trDate, @RequestParam ("thDate") Integer thDate,
                                   @RequestParam ("trValue") Integer trValue, @RequestParam ("thValue") Integer thValue,
                                   @RequestParam ("active") boolean active, @PathVariable ("ftpsetting") FtpSetting ftpSetting) {
        ftpSetting.setLogin(login);
        ftpSetting.setPassword(encoder.encodeToString(password.getBytes()));
        ftpSetting.setHost(host);
        ftpSetting.setPort(port);
        ftpSetting.setFilename(filename);
        ftpSetting.setTimeTask(timeTask);
        ftpSetting.setDashboardParam(dashboardParam);
        ftpSetting.setThDate(thDate);
        ftpSetting.setTrDate(trDate);
        ftpSetting.setTrValue(trValue);
        ftpSetting.setThValue(thValue);
        ftpService.activateFtpSetting(ftpSetting.getId(), active, v1service);
        ftpService.updateFtpSetting(ftpSetting);
        return "redirect:/dash/ftpsettingEdit/"+ftpSetting.getId();
    }
    @PostMapping("/checkFtp/{ftpsetting}")
    public String checkFtp(@PathVariable ("ftpsetting") FtpSetting ftpSetting, Model model) throws IOException {
        FtpConnector ftpConnector = new FtpConnector();
        HashMap<String, String> map = new HashMap<>();
        map.put("host", ftpSetting.getHost());
        map.put("port", ftpSetting.getPort());
        map.put("login", ftpSetting.getLogin());
        map.put("password", new String(decoder.decode(ftpSetting.getPassword())));
        model.addAttribute("message", ftpConnector.checkConnection(map));
        model.addAttribute("ftpsetting", ftpSetting);
        model.addAttribute("password", new String(decoder.decode(ftpSetting.getPassword())));
        model.addAttribute("dashboardparams", dashboardParamService.list());
        model.addAttribute("dashparam", ftpSetting.getDashboardParam());
        model.addAttribute("params",dashboardParamService.list());
        model.addAttribute("isAdmin", userService.getUserByPrincipal().isAdmin());
        return "ftpsettingEdit";
    }
    @GetMapping("/ftpsettingNew")
    public String ftpsettingNew(Model model){
        model.addAttribute("title", "new ftpsetting");
        model.addAttribute("dashboardparams", dashboardParamService.list());
        model.addAttribute("isAdmin", userService.getUserByPrincipal().isAdmin());
        return "ftpsettingNew";
    }
    @PostMapping("/createNewSetting")
      public String createNewSetting(@RequestParam ("login") String login, @RequestParam ("host") String host,
                                     @RequestParam ("port") String port, @RequestParam ("filename") String filename,
                                     @RequestParam ("password") String password, @RequestParam ("timetask") LocalTime timeTask,
                                     @RequestParam ("dashboardparam") DashboardParam dashboardParam,
                                     @RequestParam ("trDate") Integer trDate, @RequestParam ("thDate") Integer thDate,
                                     @RequestParam ("trValue") Integer trValue, @RequestParam ("thValue") Integer thValue,
                                     @RequestParam ("active") boolean active){
        FtpSetting ftpSetting = new FtpSetting();
        ftpSetting.setLogin(login);
        ftpSetting.setPassword(encoder.encodeToString(password.getBytes()));
        ftpSetting.setHost(host);
        ftpSetting.setPort(port);
        ftpSetting.setFilename(filename);
        ftpSetting.setTimeTask(timeTask);
        ftpSetting.setActive(active);
        ftpSetting.setDashboardParam(dashboardParam);
        ftpSetting.setThDate(thDate);
        ftpSetting.setTrDate(trDate);
        ftpSetting.setTrValue(trValue);
        ftpSetting.setThValue(thValue);
        ftpService.updateFtpSetting(ftpSetting);
        return "redirect:/dash/ftpsettingEdit/" + ftpSetting.getId();
    }

    @PostMapping("/deleteFtpsetting/{ftpsetting}")
    public String deleteSetting(@PathVariable ("ftpsetting") FtpSetting ftpSetting){
        ftpService.deleteFtpSetting(ftpSetting.getId());
        return "redirect:/dash/ftpsettingList";
    }
    //__________________________________________________________________________________________________
    @GetMapping("/dashboardparamList")
    public String getDashboardParamList(Model model) {
        model.addAttribute("title", "dashboardparamlist");
        model.addAttribute("dashboardParams", dashboardParamService.list());
        model.addAttribute("params",dashboardParamService.list());
        model.addAttribute("isAdmin", userService.getUserByPrincipal().isAdmin());
        return "dashboardparamList";
    }
    @GetMapping("/dashboardparamEdit/{dashboardparam}")
        public String getDashboardparamEdit(@PathVariable ("dashboardparam") DashboardParam dashboardParam, Model model){
        model.addAttribute("title", "dashbordparam");
        model.addAttribute("dashboardparam", dashboardParam);
        model.addAttribute("params",dashboardParamService.list());
        model.addAttribute("isAdmin", userService.getUserByPrincipal().isAdmin());
        return "dashboardparamEdit";
    }
    @PostMapping("/updateDashboardparam/{dashboardparam}")
    public String updateDashboardParam(@RequestParam ("name") String name, @PathVariable ("dashboardparam")DashboardParam dashboardParam) {
        dashboardParam.setName(name);
        dashboardParamService.updateDashboardParam(dashboardParam);
        return "redirect:/dash/dashboardparamList";
    }
    @PostMapping("/createNewDashboardparam")
    public String createNewDashboardparam(@RequestParam ("name") String name) {
        DashboardParam dashboardParam = new DashboardParam();
        dashboardParam.setName(name);
        dashboardParamService.updateDashboardParam(dashboardParam);
        return "redirect:/dash/dashboardparamList";
    }
    @GetMapping("/dashboardparamNew")
    public String dashboardparamNew(Model model){
        model.addAttribute("title", "new dashboardparam");
        model.addAttribute("params",dashboardParamService.list());
        model.addAttribute("isAdmin", userService.getUserByPrincipal().isAdmin());
        return "dashboardparamNew";
    }
    @PostMapping("/deleteDashboardparam/{dashboardparam}")
    public String deleteDashboardparam(@PathVariable ("dashboardparam") DashboardParam dashboardParam) {
        dashboardParamService.deleteDashboardParam(dashboardParam.getId());
        return "redirect:/dash/dashboardparamList";
    }
    //________________________________________________________________________________

    @GetMapping("/dashboardV1/{p}/{dashboardParamId}")
    public String getPeriodSettingV1(@PathVariable ("p") Long p, @PathVariable ("dashboardParamId") Long id){
        DashboardParam dashboardParam = dashboardParamService.getById(id);
        if(dashboardParam.getId()==1000000000L) return "redirect:/dash/dashboardV1";
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
        return "redirect:/dash/dashboardV1";
    }


    @GetMapping("/dashboard/{p}/{dashboardParamId}")
    public String getPeriodSetting(@PathVariable ("p") Long p, @PathVariable ("dashboardParamId") Long id){
        DashboardParam dashboardParam = dashboardParamService.getById(id);
        if(dashboardParam.getId()==1000000000L) return "redirect:/dash/dashboard";
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
        return "redirect:/dash/dashboard/"+dashboardParam.getId();

    }

    @GetMapping("/typeLine/{p}/{dashboardParamId}")
    public String getLineSetting(@PathVariable ("p") Long p, @PathVariable ("dashboardParamId") Long id){
        DashboardParam dashboardParam = dashboardParamService.getById(id);
        if(dashboardParam.getId()==1000000000L) return "redirect:/dash/dashboard";
        LineSetting lineSetting = dashboardTypeLineService.getTypeLineByUserAndParam(dashboardParam);
        if (p == 1L) {
           lineSetting.setTypeLine(TypeLine.LINE);
        } else if (p == 2L) {
            lineSetting.setTypeLine(TypeLine.BAR);
        } else if (p == 3L) {
            lineSetting.setTypeLine(TypeLine.LINE_AREA);
        } else if (p == 4L) {
            lineSetting.setTypeLine(TypeLine.LINE_REGRESS);
        } else {
            lineSetting.setTypeLine(TypeLine.LINE);
        }
        dashboardTypeLineService.updateDashboardTypeline(lineSetting);
        return "redirect:/dash/dashboard/"+dashboardParam.getId();

    }


}

