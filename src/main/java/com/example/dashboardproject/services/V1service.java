package com.example.dashboardproject.services;

import com.example.dashboardproject.models.DashboardV1;
import com.example.dashboardproject.repositories.DashboardV1repository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class V1service {

    private final DashboardV1repository dashboardV1repository;

    public List<Map> listDashboardV1(){
        List<Map> listDashboardV1 = new LinkedList<>();
        for (long i = 1; i <= dashboardV1repository.findAll().size(); i++) {
            Map<String,String> map = new HashMap<>();
            DashboardV1 dashboardV1 =  dashboardV1repository.getById(i);
            map.put("date",dashboardV1.getDate());
            map.put("value", dashboardV1.getValue());
            listDashboardV1.add(map);
        }
        return listDashboardV1;
    }

    public DashboardV1 getDashboardV1ById(Long id){
        return dashboardV1repository.getById(id);
    }
}

