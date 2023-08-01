package com.example.dashboardproject.services;

import com.example.dashboardproject.models.*;
import com.example.dashboardproject.repositories.GroupPeriodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GroupPeriodService {

    private final GroupPeriodRepository groupPeriodRepository;

    public PeriodSettingGroup getByID(Long id) {
        if (groupPeriodRepository.findAll().isEmpty()) {
            PeriodSettingGroup periodSettingGroup = new PeriodSettingGroup();
            periodSettingGroup.setDashboardPeriod(DashboardPeriod.PERIOD_WEEK);
            periodSettingGroup.setId(1L);
            return periodSettingGroup;
        } else {
            return groupPeriodRepository.getById(id);
        }
    }

    public void updateGroupPeriod(PeriodSettingGroup periodSettingGroup) {groupPeriodRepository.save(periodSettingGroup);
    }

    public String getPeriodByUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<PeriodSettingGroup> periodSettingGroups = groupPeriodRepository.findAll();
        if (!periodSettingGroups.isEmpty()) {
            for (PeriodSettingGroup periodSettingGroup : periodSettingGroups) {
                if (Objects.equals(periodSettingGroup.getUser().getId(), user.getId())) {
                    return switch (periodSettingGroup.getDashboardPeriod()) {
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

    public PeriodSettingGroup getPeriodByUserAndParam(GroupParam groupParam) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<PeriodSettingGroup> periodSettingGroups = groupPeriodRepository.findAll();
        if (!periodSettingGroups.isEmpty()) {
            for (PeriodSettingGroup periodSettingGroup : periodSettingGroups) {
                if ((Objects.equals(periodSettingGroup.getGroupParam().getId(), groupParam.getId())) &&
                        (Objects.equals(periodSettingGroup.getUser().getId(), user.getId()))) {
                    return periodSettingGroup;
                }
            }
            PeriodSettingGroup periodSettingGroup1 = new PeriodSettingGroup();
            periodSettingGroup1.setGroupParam(groupParam);
            periodSettingGroup1.setUser(user);
            return periodSettingGroup1;
        } else {
            PeriodSettingGroup periodSettingGroup2 = new PeriodSettingGroup();
            periodSettingGroup2.setGroupParam(groupParam);
            periodSettingGroup2.setUser(user);
            return periodSettingGroup2;
        }
    }
}
