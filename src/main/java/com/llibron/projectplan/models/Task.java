package com.llibron.projectplan.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
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
