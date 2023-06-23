package com.example.dashboardproject.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalTime;

@Entity
@Table(name = "ftpstting")
@Data
public class FtpSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "host")
    private String host;

    @Column(name = "port")
    private String port;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "filename")
    private String filename;

    @Column(name = "timeTask")
    private LocalTime timeTask;

    @Column(name = "active")
    private boolean active;

    @Column(name = "trDate")
    private int thDate;

    @Column(name = "thDate")
    private int trDate;

    @Column(name = "trValue")
    private int trValue;

    @Column(name = "thValue")
    private int thValue;

    @OneToOne(cascade = CascadeType.REFRESH,
            fetch = FetchType.LAZY)
    @JoinColumn
    private DashboardParam dashboardParam;

}

