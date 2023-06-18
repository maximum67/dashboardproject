package com.example.dashboardproject.services;

import com.example.dashboardproject.models.DashboardParam;
import com.example.dashboardproject.models.DashboardV1;
import com.example.dashboardproject.repositories.DashboardV1repository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
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
            map.put("date", String.valueOf(dashboardV1.getDate()));
            map.put("value", dashboardV1.getValue());
            listDashboardV1.add(map);
        }
        return listDashboardV1;
    }

    public DashboardV1 getDashboardV1ById(Long id){
        return dashboardV1repository.getById(id);
    }

    public void updateDashboardV1(DashboardV1 dashboardV1){
        dashboardV1repository.save(dashboardV1);
    }

    public List<Map> listDashboardV1ByParam(DashboardParam dashboardParam){
        List<Map> listDashboardV1 = new LinkedList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        Sort sort = Sort.by("date").ascending();
        List<DashboardV1> dashboardV1s = dashboardV1repository.findAllByDashboardParam(dashboardParam, sort);
        for (DashboardV1 dashboardV1 : dashboardV1s) {
            Map<String,String> map = new HashMap<>();
            map.put("date" , formatter.format(dashboardV1.getDate()));
            map.put("value", dashboardV1.getValue());
            listDashboardV1.add(map);
            }
       return listDashboardV1;
    }
    public List<Map> listDashboardV1ByParamPeriod(DashboardParam dashboardParam, Integer p){
        List<Map> listDashboardV1 = new LinkedList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        Sort sort = Sort.by("date").ascending();
        List<DashboardV1> dashboardV1s = dashboardV1repository.findAllByDashboardParam(dashboardParam, sort);
        for (DashboardV1 dashboardV1 : dashboardV1s) {
            Map<String, String> map = new HashMap<>();
            map.put("date", formatter.format(dashboardV1.getDate()));
            map.put("value", dashboardV1.getValue());
            listDashboardV1.add(map);
        }
        int end = listDashboardV1.size();
        if (p < listDashboardV1.size()) {
            return  listDashboardV1.subList(end-p,end);
        }
        return listDashboardV1;
    }

}

