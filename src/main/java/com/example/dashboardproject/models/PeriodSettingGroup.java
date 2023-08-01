package com.example.dashboardproject.models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;

@Entity
@Table(name="periodSettingGroup")
@Data
public class PeriodSettingGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn
    private User user;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn
    private GroupParam groupParam;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name="periodGroup", joinColumns = @JoinColumn(name="periodSettingGroup_id"))
    private DashboardPeriod dashboardPeriod = DashboardPeriod.PERIOD_WEEK;

}
