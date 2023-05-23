package com.example.dashboardproject.rest;

import com.example.dashboardproject.services.V1service;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/dashboard")
public class DashboardV1RestController {

    private final V1service v1service;

    @GetMapping("/V1")
    public ResponseEntity<List> findAll() {
        List<Map> list = v1service.listDashboardV1();
        return new ResponseEntity<List>(list, HttpStatus.OK);
    }
}
