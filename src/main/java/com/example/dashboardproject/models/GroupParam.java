package com.example.dashboardproject.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="groupParam")
@Data
public class GroupParam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="groupParam", unique = true)
    private String groupName;

    @ManyToMany(
            fetch = FetchType.EAGER)
    @JoinTable(
            name = "dashboard_group_params",
            joinColumns = @JoinColumn(name="group_param_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="param_dashboard_id", referencedColumnName = "id"))
    private List<DashboardParam> dashboardParams = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, mappedBy = "groupParam")
    private List<PeriodSettingGroup> periodSettingGroupList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, mappedBy = "groupParam")
    private List<LineSettingGroup> lineSettingGroupList = new ArrayList<>();

    @OneToMany(cascade=CascadeType.ALL,
            fetch = FetchType.EAGER,mappedBy = "groupParam")
    private List<HiddenSettingGroup> hiddenSettingGroupList = new ArrayList<>();

    public void addDashboardParam(DashboardParam dashboardParam) {
        dashboardParams.add(dashboardParam);
        dashboardParam.getGroupParams().add(this);
    }
}
