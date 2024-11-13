package com.llibron.projectplan.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private int duration;

    @OneToMany
    @JoinColumn(name = "dependencies")
    private List<Task> dependencies;

    private Long projectId;

    private LocalDate startDate;

    private LocalDate endDate;

}
