package com.example.dashboardproject.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "periods")
@Data
public class PeriodSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn
    private User user;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn
    private DashboardParam dashboardParam;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "dashboard_period", joinColumns = @JoinColumn(name = "periodSetting_id"))
    private DashboardPeriod dashboardPeriod = DashboardPeriod.PERIOD_WEEK;
}
