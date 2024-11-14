package com.llibron.projectplan.models;

import com.llibron.projectplan.utilities.jackson.LongListToJsonConverter;
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

    @Convert(converter = LongListToJsonConverter.class)
    private List<Long> dependencies;

    @ManyToOne
    private Project project;

    private LocalDate startDate;

    private LocalDate endDate;

}
