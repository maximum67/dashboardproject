package com.example.dashboardproject.models;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="typelines")
@Data
public class LineSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn
    private User user;

    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn
    private DashboardParam dashboardParam;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name="type_line", joinColumns = @JoinColumn(name="typeLine_id"))
    private TypeLine typeLine = TypeLine.LINE;
}
