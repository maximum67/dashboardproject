package com.example.dashboardproject.repositories;

import com.example.dashboardproject.models.PeriodSettingGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupPeriodRepository extends JpaRepository<PeriodSettingGroup, Long> {
}
