package com.example.dashboardproject.controller;



import com.example.dashboardproject.models.DashboardParam;
import com.example.dashboardproject.models.FtpSetting;
import com.example.dashboardproject.models.Role;
import com.example.dashboardproject.models.User;
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
    private final DashboardParamService dashboardParamService;
    Base64.Decoder decoder = Base64.getDecoder();
    Base64.Encoder encoder = Base64.getEncoder();

    @GetMapping("/dashboardV1")
    public String getDashboard(Model model) {
         model.addAttribute("title","Dashboard");
         return "dashboardV1";
     }
    @GetMapping("/ftpsettingList")
    public String getFtpSettingList(Model model) {
        model.addAttribute("title","ftpsettingList");
        model.addAttribute("settings",ftpService.list());
        return "ftpsettingList";
    }
    @GetMapping("/ftpsettingEdit/{ftpsetting}")
    public String getFtpSettingEdit(@PathVariable ("ftpsetting") FtpSetting ftpSetting, Model model) {
        model.addAttribute("title","ftpsettingEdit");
        model.addAttribute("ftpsetting", ftpSetting);
        model.addAttribute("password", new String(decoder.decode(ftpSetting.getPassword())));
        model.addAttribute("dashboardparams", dashboardParamService.list());
        model.addAttribute("dashparam", ftpSetting.getDashboardParam());
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
        return "ftpsettingEdit";
    }
    @GetMapping("/ftpsettingNew")
    public String ftpsettingNew(Model model){
        model.addAttribute("title", "new ftpsetting");
        model.addAttribute("dashboardparams", dashboardParamService.list());
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
        return "dashboardparamList";
    }
    @GetMapping("/dashboardparamEdit/{dashboardparam}")
        public String getDashboardparamEdit(@PathVariable ("dashboardparam") DashboardParam dashboardParam, Model model){
        model.addAttribute("title", "dashbordparam");
        model.addAttribute("dashboardparam", dashboardParam);
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
        return "dashboardparamNew";
    }
    @PostMapping("/deleteDashboardparam/{dashboardparam}")
    public String deleteDashboardparam(@PathVariable ("dashboardparam") DashboardParam dashboardParam) {
        dashboardParamService.deleteDashboardparam(dashboardParam.getId());
        return "redirect:/dash/dashboardparamList";
    }
}

