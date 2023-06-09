package com.example.dashboardproject.rest;

import com.example.dashboardproject.models.DashboardParam;
import com.example.dashboardproject.services.DashboardPeriodService;
import com.example.dashboardproject.services.DashboardTypeLineService;
import com.example.dashboardproject.services.V1service;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/dashboard")
public class DashboardV1RestController {

    private final V1service v1service;
    private final DashboardPeriodService dashboardPeriodService;
    private final DashboardTypeLineService dashboardTypeLineService;

    @GetMapping("/V1/{dashboardParamId}")
    public ResponseEntity<List> findByParam(@PathVariable("dashboardParamId") DashboardParam dashboardParam) {
        List<Map> list = v1service.listDashboardV1ByParam(dashboardParam);
        return new ResponseEntity<List>(list, HttpStatus.OK);
    }

    @GetMapping("/V2/{dashboardParamId}/{p}")
    public ResponseEntity<List> findByParamPeriod(@PathVariable("dashboardParamId") DashboardParam dashboardParam,
                                                  @PathVariable("p") Integer p) {
        List<Map> list = v1service.listDashboardV1ByParamPeriod(dashboardParam, p);
        return new ResponseEntity<List>(list, HttpStatus.OK);
    }

    @GetMapping("/V3/{dashboardParamId}")
    public ResponseEntity<Long> getPeriod(@PathVariable("dashboardParamId") DashboardParam dashboardParam) {
        long periodValue = switch (dashboardPeriodService.getPeriodByUserAndParam(dashboardParam).getDashboardPeriod()) {
            case PERIOD_MONTH -> 30L;
            case PERIOD_YEAR -> 365L;
            case PERIOD_QUARTER -> 90L;
            default -> 7L;
        };
        return new ResponseEntity<>(periodValue, HttpStatus.OK);
    }

    @GetMapping("/V4/{dashboardParamId}")
    public ResponseEntity<String> getTypeLine(@PathVariable("dashboardParamId") DashboardParam dashboardParam) {
        String typeLine = switch (dashboardTypeLineService.getTypeLineByUserAndParam(dashboardParam).getTypeLine()) {
            case BAR -> "Гистограмма";
            case LINE_AREA -> "Область";
            case LINE_REGRESS -> "Область с регрессом";
            default -> "Линия";
        };
        return new ResponseEntity<>(typeLine, HttpStatus.OK);
    }
}
