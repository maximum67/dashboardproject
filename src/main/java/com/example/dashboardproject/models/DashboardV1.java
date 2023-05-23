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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "date", unique = true)
    private String date;

    @Column(name = "value")
    private String value;



}
