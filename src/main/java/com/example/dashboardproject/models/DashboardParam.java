package com.example.dashboardproject.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "param")
@Data
public class DashboardParam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "param", unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, mappedBy = "dashboardParam")
    private List<DashboardV1> dashboardV1 = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, mappedBy = "dashboardParam")
    private FtpSetting ftpSetting;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, mappedBy = "dashboardParam")
    private List<PeriodSetting> periodSettings = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, mappedBy = "dashboardParam")
    private List<LineSetting> lineSettings = new ArrayList<>();

    @OneToMany(cascade=CascadeType.ALL,
            fetch = FetchType.EAGER,mappedBy = "dashboardParam")
    private List<HiddenSetting> hiddenSetting = new ArrayList<>();
}
