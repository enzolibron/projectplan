package com.llibron.projectplan.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int duration;

    @ElementCollection
    private List<Long> dependencies;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    private LocalDate startDate;

    private LocalDate endDate;

}
