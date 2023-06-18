package com.example.dashboardproject.services;

import com.example.dashboardproject.models.DashboardParam;
import com.example.dashboardproject.models.DashboardPeriod;
import com.example.dashboardproject.models.PeriodSetting;
import com.example.dashboardproject.models.User;
import com.example.dashboardproject.repositories.DashboardPeriodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DashboardPeriodService {

    private final DashboardPeriodRepository dashboardPeriodRepository;

    public PeriodSetting getByID(Long id) {
        if (dashboardPeriodRepository.findAll().isEmpty()) {
            PeriodSetting periodSetting = new PeriodSetting();
            periodSetting.setDashboardPeriod(DashboardPeriod.PERIOD_WEEK);
            periodSetting.setId(1L);
            return periodSetting;
        } else {
            return dashboardPeriodRepository.getById(id);
        }
    }

    public void updateDashboardPeriod(PeriodSetting periodSetting){
        dashboardPeriodRepository.save(periodSetting);
    }

    public void deleteDashboardPeriod(Long id){
        dashboardPeriodRepository.deleteById(id);
    }

    public String getDashboardPeriod(Long id){
        return switch (dashboardPeriodRepository.getReferenceById(id).getDashboardPeriod()) {
            case PERIOD_MONTH -> "Период месяц";
            case PERIOD_YEAR -> "Период год";
            case PERIOD_QUARTER -> "Период квартал";
            default -> "Период неделя";
        };
    }
    public String getPeriodByUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<PeriodSetting> periodSettings = dashboardPeriodRepository.findAll();
        if (!periodSettings.isEmpty()) {
            for (PeriodSetting periodSetting : periodSettings) {
                if (Objects.equals(periodSetting.getUser().getId(), user.getId())) {
                    return switch (periodSetting.getDashboardPeriod()) {
                        case PERIOD_MONTH -> "Период месяц";
                        case PERIOD_YEAR -> "Период год";
                        case PERIOD_QUARTER -> "Период квартал";
                        default -> "Период неделя";
                    };
                }
            }
        }
        return "Период";
    }
    public PeriodSetting getPeriodByUserAndParam(DashboardParam dashboardParam){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<PeriodSetting> periodSettings = dashboardPeriodRepository.findAll();
        if (!periodSettings.isEmpty()){
            for (PeriodSetting periodSetting : periodSettings) {
            if ((Objects.equals(periodSetting.getDashboardParam().getId(), dashboardParam.getId())) &&
                    (Objects.equals(periodSetting.getUser().getId(), user.getId()))) {
               return periodSetting;
            }
          }
            PeriodSetting periodSetting1 = new PeriodSetting();
            periodSetting1.setDashboardParam(dashboardParam);
            periodSetting1.setUser(user);
            return periodSetting1;
       }else{
            PeriodSetting periodSetting2 = new PeriodSetting();
            periodSetting2.setDashboardParam(dashboardParam);
            periodSetting2.setUser(user);
            return periodSetting2;
        }
    }
}
