package com.example.dashboardproject.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="hiddenParamGroup")
@Data
public class HiddenSettingGroup {

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

    @Column(name="hiddenGroup")
    private Boolean isHidden = true;
}
