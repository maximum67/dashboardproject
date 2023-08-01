package com.example.dashboardproject.controller;


import com.example.dashboardproject.models.DashboardParam;
import com.example.dashboardproject.models.FtpSetting;
import com.example.dashboardproject.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Base64;
import java.util.HashMap;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequestMapping("/ftp/")
public class FtpController {

    private final FtpService ftpService;
    private final V1service v1service;
    private final UserService userService;
    private final DashboardParamService dashboardParamService;
    private final GroupParamService groupParamService;
    Base64.Decoder decoder = Base64.getDecoder();
    Base64.Encoder encoder = Base64.getEncoder();


    @GetMapping("/ftpsettingList")
    public String getFtpSettingList(Model model) {
        model.addAttribute("title", "ftpsettingList");
        model.addAttribute("settings", ftpService.list());
        model.addAttribute("params", dashboardParamService.list());
        model.addAttribute("groupParams", groupParamService.list());
        model.addAttribute("isAdmin", userService.getUserByPrincipal().isAdmin());
        return "ftpsettingList";
    }

    @GetMapping("/ftpsettingEdit/{ftpsetting}")
    public String getFtpSettingEdit(@PathVariable("ftpsetting") FtpSetting ftpSetting, Model model) {
        model.addAttribute("title", "ftpsettingEdit");
        model.addAttribute("ftpsetting", ftpSetting);
        model.addAttribute("password", new String(decoder.decode(ftpSetting.getPassword())));
        model.addAttribute("dashboardparams", dashboardParamService.list());
        model.addAttribute("dashparam", ftpSetting.getDashboardParam());
        model.addAttribute("params", dashboardParamService.list());
        model.addAttribute("groupParams", groupParamService.list());
        model.addAttribute("isAdmin", userService.getUserByPrincipal().isAdmin());
        return "ftpsettingEdit";
    }

    @PostMapping("/updateFtpsetting/{ftpsetting}")
    public String updateFtpSetting(@RequestParam("login") String login, @RequestParam("host") String host,
                                   @RequestParam("port") String port, @RequestParam("filename") String filename,
                                   @RequestParam("password") String password, @RequestParam("timetask") LocalTime timeTask,
                                   @RequestParam("dashboardparam") DashboardParam dashboardParam,
                                   @RequestParam("trDate") Integer trDate, @RequestParam("thDate") Integer thDate,
                                   @RequestParam("trValue") Integer trValue, @RequestParam("thValue") Integer thValue,
                                   @RequestParam("active") boolean active, @PathVariable("ftpsetting") FtpSetting ftpSetting) {
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
        return "redirect:/ftp/ftpsettingEdit/" + ftpSetting.getId();
    }

    @PostMapping("/checkFtp/{ftpsetting}")
    public String checkFtp(@PathVariable("ftpsetting") FtpSetting ftpSetting, Model model) throws IOException {
        FtpConnector ftpConnector = new FtpConnector();
        HashMap<String, String> map = new HashMap<>();
        map.put("host", ftpSetting.getHost());
        map.put("port", ftpSetting.getPort());
        map.put("login", ftpSetting.getLogin());
        map.put("password", new String(decoder.decode(ftpSetting.getPassword())));
        model.addAttribute("title", "ftpsettingEdit");
        model.addAttribute("message", ftpConnector.checkConnection(map));
        model.addAttribute("ftpsetting", ftpSetting);
        model.addAttribute("password", new String(decoder.decode(ftpSetting.getPassword())));
        model.addAttribute("dashboardparams", dashboardParamService.list());
        model.addAttribute("dashparam", ftpSetting.getDashboardParam());
        model.addAttribute("params", dashboardParamService.list());
        model.addAttribute("groupParams", groupParamService.list());
        model.addAttribute("isAdmin", userService.getUserByPrincipal().isAdmin());
        return "ftpsettingEdit";
    }

    @GetMapping("/ftpsettingNew")
    public String ftpsettingNew(Model model) {
        model.addAttribute("title", "new ftpsetting");
        model.addAttribute("dashboardparams", dashboardParamService.list());
        model.addAttribute("params", dashboardParamService.list());
        model.addAttribute("groupParams", groupParamService.list());
        model.addAttribute("isAdmin", userService.getUserByPrincipal().isAdmin());
        return "ftpsettingNew";
    }

    @PostMapping("/createNewSetting")
    public String createNewSetting(@RequestParam("login") String login, @RequestParam("host") String host,
                                   @RequestParam("port") String port, @RequestParam("filename") String filename,
                                   @RequestParam("password") String password, @RequestParam("timetask") LocalTime timeTask,
                                   @RequestParam("dashboardparam") DashboardParam dashboardParam,
                                   @RequestParam("trDate") Integer trDate, @RequestParam("thDate") Integer thDate,
                                   @RequestParam("trValue") Integer trValue, @RequestParam("thValue") Integer thValue,
                                   @RequestParam("active") boolean active) {
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
        return "redirect:/ftp/ftpsettingEdit/" + ftpSetting.getId();
    }

    @PostMapping("/deleteFtpsetting/{ftpsetting}")
    public String deleteSetting(@PathVariable("ftpsetting") FtpSetting ftpSetting) {
        ftpService.deleteFtpSetting(ftpSetting.getId());
        return "redirect:/ftp/ftpsettingList";
    }


}
