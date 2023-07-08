package com.example.dashboardproject.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="hiddenParam")
@Data
public class HiddenSetting {

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

    @Column(name = "hidden")
    private Boolean isHidden = true;
}
