package com.example.dashboardproject.repositories;

import com.example.dashboardproject.models.LineSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DashboardTypeLineRepository extends JpaRepository<LineSetting, Long> {
}
