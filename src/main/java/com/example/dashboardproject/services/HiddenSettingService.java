package com.example.dashboardproject.services;

import com.example.dashboardproject.models.DashboardParam;
import com.example.dashboardproject.models.HiddenSetting;
import com.example.dashboardproject.models.User;
import com.example.dashboardproject.repositories.HiddenSettingRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
@AllArgsConstructor
public class HiddenSettingService {

    private final HiddenSettingRepository hiddenSettingRepository;

    public HiddenSetting getHiddenSettingByUserAndParam(DashboardParam dashboardParam){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<HiddenSetting> hiddenSettings = hiddenSettingRepository.findAll();
        if (!hiddenSettings.isEmpty()){
            for (HiddenSetting hiddenSetting: hiddenSettings){
                if (Objects.equals(hiddenSetting.getDashboardParam().getId(), dashboardParam.getId()) &&
                        Objects.equals(hiddenSetting.getUser().getId(), user.getId())){
                    return hiddenSetting;
                }
            }
            HiddenSetting hiddenSetting1 = new HiddenSetting();
            hiddenSetting1.setDashboardParam(dashboardParam);
            hiddenSetting1.setUser(user);
            hiddenSetting1.setIsHidden(true);
            hiddenSettingRepository.save(hiddenSetting1);
            return hiddenSetting1;
        }else{
            HiddenSetting hiddenSetting2 = new HiddenSetting();
            hiddenSetting2.setDashboardParam(dashboardParam);
            hiddenSetting2.setUser(user);
            hiddenSetting2.setIsHidden(true);
            hiddenSettingRepository.save(hiddenSetting2);
            return hiddenSetting2;
        }
    }

    public void updateHiddenSetting (HiddenSetting hiddenSetting){ hiddenSettingRepository.save(hiddenSetting);}


}
