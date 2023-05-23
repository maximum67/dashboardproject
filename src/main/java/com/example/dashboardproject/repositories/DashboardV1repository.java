package com.example.dashboardproject.repositories;


import com.example.dashboardproject.models.DashboardV1;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DashboardV1repository extends JpaRepository<DashboardV1, Long> {
    DashboardV1 getById(Long id);
}
