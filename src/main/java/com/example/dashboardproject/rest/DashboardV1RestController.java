package com.example.dashboardproject.rest;

import com.example.dashboardproject.models.DashboardParam;
import com.example.dashboardproject.models.GroupParam;
import com.example.dashboardproject.services.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@RestController
@AllArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN') || hasAuthority('ROLE_USER')")
@RequestMapping("/api/dashboard")
public class DashboardV1RestController {

    private final V1service v1service;
    private final DashboardPeriodService dashboardPeriodService;
    private final DashboardTypeLineService dashboardTypeLineService;
    private final GroupParamService groupParamService;
    private final GroupPeriodService groupPeriodService;
    private  final GroupTypeLineService groupTypeLineService;

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
            case BAR3D -> "Гистограмма 3D";
            case TEXT -> "Текст";
            case PIE -> "Круг";
            case DONUT3D-> "Кольцо 3D";
            case LINE_AREA -> "Область";
            case LINE_REGRESS -> "Область с регрессом";
            default -> "Линия";
        };
        return new ResponseEntity<>(typeLine, HttpStatus.OK);
    }

    @GetMapping("/V5/{dashboardParamId}/{p}")
    public ResponseEntity<List> findByParamPeriodV5(@PathVariable("dashboardParamId") DashboardParam dashboardParam,
                                                  @PathVariable("p") Integer p) {
        List<Map> list = v1service.listDashboardV1ByParamPeriod(dashboardParam, p);
        return new ResponseEntity<List>(list, HttpStatus.OK);
    }

    @GetMapping("/V6/{groupParamId}/{p}")
    public ResponseEntity<List> findByGroupAndPeriod(@PathVariable("groupParamId") GroupParam groupParam,
                                                  @PathVariable("p") Integer p) {
        List<List> list = v1service.listGroupParamByParamsAndPeriod (groupParam, p);
        return new ResponseEntity<List>(list, HttpStatus.OK);
    }

    @GetMapping("/V7/{groupParamId}")
    public ResponseEntity<List> findByGroup(@PathVariable("groupParamId") GroupParam groupParam) {
        List<String> list = groupParamService.listParamsByGroupName(groupParam);
        return new ResponseEntity<List>(list, HttpStatus.OK);
    }

    @GetMapping("/V8/{groupParamId}")
    public ResponseEntity<Long> getGroupPeriod(@PathVariable("groupParamId") GroupParam groupParam) {
        long periodValue = switch (groupPeriodService.getPeriodByUserAndParam(groupParam).getDashboardPeriod()) {
            case PERIOD_MONTH -> 30L;
            case PERIOD_YEAR -> 365L;
            case PERIOD_QUARTER -> 90L;
            default -> 7L;
        };
        return new ResponseEntity<>(periodValue, HttpStatus.OK);
    }
    @GetMapping("/V9/{groupParamId}")
    public ResponseEntity<String> getGroupTypeLine(@PathVariable("groupParamId") GroupParam groupParam) {
        String typeLine = switch (groupTypeLineService.getTypeLineByUserAndParam(groupParam).getTypeLine()) {
            case BAR -> "Гистограмма";
            case BAR3D -> "Гистограмма 3D";
            case TEXT -> "Текст";
            case PIE -> "Круг";
            case DONUT3D-> "Кольцо 3D";
            case LINE_AREA -> "Область";
            case LINE_REGRESS -> "Область с регрессом";
            default -> "Линия";
        };
        return new ResponseEntity<>(typeLine, HttpStatus.OK);
    }

    @GetMapping("/V10/{groupParamId}")
    public ResponseEntity<List> findByGroupId(@PathVariable("groupParamId") GroupParam groupParam) {
        List<Map> list = groupParamService.listParamsByGroupId(groupParam);
        return new ResponseEntity<List>(list, HttpStatus.OK);
    }
}
