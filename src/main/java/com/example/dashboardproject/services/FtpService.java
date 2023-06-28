package com.example.dashboardproject.services;

import com.example.dashboardproject.controller.fileParsing.ThreadTaskFileParsing;
import com.example.dashboardproject.models.FtpSetting;
import com.example.dashboardproject.repositories.FtpSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class FtpService {

    private final FtpSettingRepository ftpSettingRepository;
    private ThreadTaskFileParsing threadTaskFileParsing;
    private HashMap<Long, ThreadTaskFileParsing> map = new HashMap<>();

    public void deleteFtpSetting(Long id) {
        ftpSettingRepository.deleteById(id);
    }

    public void activateFtpSetting(Long id, boolean active, V1service v1service) {
        FtpSetting ftpSetting = ftpSettingRepository.findById(id).orElse(null);
        if (active) {
            ftpSetting.setActive(true);
            if (!map.containsKey(ftpSetting.getId())) {
                threadTaskFileParsing = new ThreadTaskFileParsing(ftpSetting, v1service);
                threadTaskFileParsing.start();
                map.put(ftpSetting.getId(), threadTaskFileParsing);
            }
        } else {
            ftpSetting.setActive(false);
            if (map.containsKey(ftpSetting.getId())) {
                threadTaskFileParsing = map.get(ftpSetting.getId());
                threadTaskFileParsing.interrupt();
                map.remove(ftpSetting.getId());
            }
        }
        ftpSettingRepository.save(ftpSetting);
    }

    public void updateFtpSetting(FtpSetting ftpSetting) {
        ftpSettingRepository.save(ftpSetting);
    }

    public List<FtpSetting> list() {
        return ftpSettingRepository.findAll();
    }

}
