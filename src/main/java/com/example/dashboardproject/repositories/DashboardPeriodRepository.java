package com.example.dashboardproject.repositories;

import com.example.dashboardproject.models.PeriodSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DashboardPeriodRepository extends JpaRepository<PeriodSetting, Long> {
}
