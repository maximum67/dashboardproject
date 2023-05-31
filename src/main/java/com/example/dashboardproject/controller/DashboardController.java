package com.example.dashboardproject.controller;



import com.example.dashboardproject.models.FtpSetting;
import com.example.dashboardproject.models.Role;
import com.example.dashboardproject.models.User;
import com.example.dashboardproject.repositories.FtpSettingRepository;
import com.example.dashboardproject.services.FtpConnector;
import com.example.dashboardproject.services.FtpService;
import com.example.dashboardproject.services.UserService;
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
    private final FtpSettingRepository ftpSettingRepository;

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
        Base64.Decoder decoder = Base64.getDecoder();
        model.addAttribute("password", decoder.decode(ftpSetting.getPassword()));
        return "ftpsettingEdit";
    }
    @PostMapping("/updateFtpsetting/{ftpsetting}")
    public String updateFtpSetting(@RequestParam ("login") String login, @RequestParam ("host") String host,
                                   @RequestParam ("port") String port, @RequestParam ("filename") String filename,
                                   @RequestParam ("password") String password, @RequestParam ("timetask") LocalTime timeTask,
                                   @RequestParam ("active") boolean active, @PathVariable ("ftpsetting") FtpSetting ftpSetting){
        ftpSetting.setLogin(login);
        Base64.Encoder encoder = Base64.getEncoder();
        ftpSetting.setPassword(encoder.encodeToString(password.getBytes(StandardCharsets.UTF_8)));
        ftpSetting.setHost(host);
        ftpSetting.setPort(port);
        ftpSetting.setFilename(filename);
        ftpSetting.setTimeTask(timeTask);
        ftpService.activateFtpSetting(ftpSetting.getId(), active);
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
        Base64.Decoder decoder = Base64.getDecoder();
        map.put("password", new String(decoder.decode(ftpSetting.getPassword())));
        model.addAttribute("message", ftpConnector.checkConnection(map));
        model.addAttribute("ftpsetting", ftpSetting);
        model.addAttribute("password", decoder.decode(ftpSetting.getPassword()));
        return "ftpsettingEdit";
    }
    @GetMapping("/ftpsettingNew")
    public String ftpsettingNew(Model model){
        model.addAttribute("title", "new ftpsetting");
        return "ftpsettingNew";
    }
    @PostMapping("/createNewSetting")
      public String createNewSetting(@RequestParam ("login") String login, @RequestParam ("host") String host,
                                     @RequestParam ("port") String port, @RequestParam ("filename") String filename,
                                     @RequestParam ("password") String password, @RequestParam ("timetask") LocalTime timeTask,
                                     @RequestParam ("active") boolean active){
        FtpSetting ftpSetting = new FtpSetting();
        ftpSetting.setLogin(login);
        Base64.Encoder encoder = Base64.getEncoder();
        ftpSetting.setPassword(encoder.encodeToString(password.getBytes(StandardCharsets.UTF_8)));
        ftpSetting.setHost(host);
        ftpSetting.setPort(port);
        ftpSetting.setFilename(filename);
        ftpSetting.setTimeTask(timeTask);
        ftpSetting.setActive(active);
        ftpService.updateFtpSetting(ftpSetting);
        return "redirect:/dash/ftpsettingEdit/" + ftpSetting.getId();
   }

    @PostMapping("/deleteFtpsetting/{ftpsetting}")
    public String deleteSetting(@PathVariable ("ftpsetting") FtpSetting ftpSetting){
        ftpService.deleteFtpSetting(ftpSetting.getId());
        return "redirect:/dash/ftpsettingList";
    }
}

