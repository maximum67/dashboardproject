package com.example.dashboardproject.repositories;

import com.example.dashboardproject.models.HiddenSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HiddenSettingRepository extends JpaRepository<HiddenSetting, Long> {

}
