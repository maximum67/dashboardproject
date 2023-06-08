package com.example.dashboardproject.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dashV1")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardV1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private String date;

    @Column(name = "value")
    private String value;

    @ManyToOne(cascade = CascadeType.REFRESH,
            fetch = FetchType.EAGER)
    @JoinColumn
    private DashboardParam dashboardParam;

}
