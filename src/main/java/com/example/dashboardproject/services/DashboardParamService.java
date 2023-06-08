package com.example.dashboardproject.services;


import com.example.dashboardproject.models.DashboardParam;
import com.example.dashboardproject.models.DashboardV1;
import com.example.dashboardproject.repositories.DashboardParamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardParamService {

    private final DashboardParamRepository dashboardParamRepository;

    public List<DashboardParam> list() { return dashboardParamRepository.findAll();}

    public void updateDashboardParam(DashboardParam dashboardParam) {
        dashboardParamRepository.save(dashboardParam);
    }

    public void deleteDashboardparam(Long id) { dashboardParamRepository.deleteById(id);}

}
