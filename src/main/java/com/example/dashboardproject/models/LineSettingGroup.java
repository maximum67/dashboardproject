package com.example.dashboardproject.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="typeLinesGroup")
@Data
public class LineSettingGroup {

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
    @CollectionTable(name="type_Line", joinColumns = @JoinColumn(name="typeLine_id"))
    private TypeLine typeLine = TypeLine.LINE;
}
