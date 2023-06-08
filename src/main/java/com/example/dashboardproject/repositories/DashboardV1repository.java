package com.example.dashboardproject.repositories;


import com.example.dashboardproject.models.DashboardParam;
import com.example.dashboardproject.models.DashboardV1;
import com.example.dashboardproject.models.FtpSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface DashboardV1repository extends JpaRepository<DashboardV1, Long> {
    DashboardV1 getById(Long id);

    List<DashboardV1> findAllByDashboardParam(DashboardParam dashboardParam);
}
