package com.example.dashboardproject.services;

import com.example.dashboardproject.repositories.DashboardV1repository;
import com.example.dashboardproject.services.fileParsing.ThreadTaskFileParsing;
import com.example.dashboardproject.models.FtpSetting;
import com.example.dashboardproject.repositories.FtpSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class FtpService {

    private final FtpSettingRepository ftpSettingRepository;
    private final DashboardV1repository dashboardV1repository;
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

    @Component
    class StartTask implements ApplicationRunner{

        @Override
        public void run(ApplicationArguments args) throws Exception {
            List<FtpSetting> ftpSettings = ftpSettingRepository.findAll();
            for (FtpSetting ftpSetting : ftpSettings) {
                if (ftpSetting.isActive()) {
                   V1service v1service = new V1service(dashboardV1repository);
                    activateFtpSetting(ftpSetting.getId(), true, v1service);
                }
            }
        }
    }
}
