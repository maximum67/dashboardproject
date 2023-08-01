package com.example.dashboardproject.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@Table(name = "param")
@Getter
@Setter
@RequiredArgsConstructor
public class DashboardParam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "param", unique = true)
    private String name;

    @Column(name ="icon")
    private  String icon ="filter_drama";

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, mappedBy = "dashboardParam")
    private List<DashboardV1> dashboardV1 = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, mappedBy = "dashboardParam")
    private List<FtpSetting> ftpSetting = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, mappedBy = "dashboardParam")
    private List<PeriodSetting> periodSettings = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, mappedBy = "dashboardParam")
    private List<LineSetting> lineSettings = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, mappedBy = "dashboardParam")
    private List<HiddenSetting> hiddenSetting = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.REFRESH,
            fetch = FetchType.EAGER)
    @JoinTable(
            name = "dashboard_group_params",
            joinColumns = @JoinColumn(name = "param_dashboard_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "group_param_id", referencedColumnName = "id"))
    private List<GroupParam> groupParams = new ArrayList<>();
}
