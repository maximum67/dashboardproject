package com.example.dashboardproject.services;

import com.example.dashboardproject.models.DashboardParam;
import com.example.dashboardproject.models.DashboardV1;
import com.example.dashboardproject.models.GroupParam;
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

    public void updateDashboardV1(DashboardV1 dashboardV1) {
        dashboardV1repository.save(dashboardV1);
    }

    public List<Map> listDashboardV1ByParam(DashboardParam dashboardParam) {
        List<Map> listDashboardV1 = new LinkedList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");
        Sort sort = Sort.by("date").ascending();
        List<DashboardV1> dashboardV1s = dashboardV1repository.findAllByDashboardParam(dashboardParam, sort);
        for (DashboardV1 dashboardV1 : dashboardV1s) {
            Map<String, String> map = new HashMap<>();
            map.put("date", formatter.format(dashboardV1.getDate()));
            map.put("value", dashboardV1.getValue());
            listDashboardV1.add(map);
        }
        return listDashboardV1;
    }

    public List<Map> listDashboardV1ByParamPeriod(DashboardParam dashboardParam, Integer p) {
        List<Map> listDashboardV1 = new LinkedList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");
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
            return listDashboardV1.subList(end - p, end);
        }
        return listDashboardV1;
    }

    public List<Map> getParametrTable(DashboardParam dashboardParam, Integer p) {
        List<Map> listDashboardV1 = new LinkedList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");
        Sort sort = Sort.by("date").ascending();
        List<DashboardV1> dashboardV1s = dashboardV1repository.findAllByDashboardParam(dashboardParam, sort);
        for (int i = dashboardV1s.size() - 1; i >= 0; i--) {
            Map<String, String> map = new HashMap<>();
            map.put("date", formatter.format(dashboardV1s.get(i).getDate()));
            map.put("value", dashboardV1s.get(i).getValue());
            if (i == 0) {
                map.put("valueDinamic", String.format("%.2f", Double.parseDouble(dashboardV1s.get(i).getValue())));
                map.put("valueDinamicPercent", "0,00%");
            } else {
                map.put("valueDinamic", String.format("%.2f", Double.parseDouble(dashboardV1s.get(i).getValue()) - Double.parseDouble(dashboardV1s.get(i - 1).getValue())));
                map.put("valueDinamicPercent", String.format("%.2f", (Double.parseDouble(dashboardV1s.get(i).getValue()) / Double.parseDouble(dashboardV1s.get(i - 1).getValue()) - 1) * 100) + "%");
            }
            listDashboardV1.add(map);
        }
        if (p < listDashboardV1.size()) {
            return listDashboardV1.subList(0, p);
        }
        return listDashboardV1;
    }

    public List<List> listGroupParamByParamsAndPeriod(GroupParam groupParam, Integer p) {
        List<List> listGroupParam = new LinkedList<>();
        for (DashboardParam dashboardParam : groupParam.getDashboardParams()) {
            List<Map> listDashboardV1 = new LinkedList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");
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
                listGroupParam.add(listDashboardV1.subList(end - p, end));
            }else {
                listGroupParam.add(listDashboardV1);
            }
        }
        return listGroupParam;
    }

}

