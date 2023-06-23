package com.example.dashboardproject.services;


import com.example.dashboardproject.models.DashboardParam;
import com.example.dashboardproject.repositories.DashboardParamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardParamService {

    private final DashboardParamRepository dashboardParamRepository;

    public List<DashboardParam> list() {
        return dashboardParamRepository.findAll();
    }

    public void updateDashboardParam(DashboardParam dashboardParam) {
        dashboardParamRepository.save(dashboardParam);
    }

    public void deleteDashboardParam(Long id) {
        dashboardParamRepository.deleteById(id);
    }

    public DashboardParam getById(Long id) {
        if (dashboardParamRepository.findAll().isEmpty() || !dashboardParamRepository.existsById(id)) {
            DashboardParam dashboardParam = new DashboardParam();
            dashboardParam.setName("Демо");
            dashboardParam.setId(1000000000L);
            return dashboardParam;
        } else {
            return dashboardParamRepository.getReferenceById(id);
        }
    }
}
