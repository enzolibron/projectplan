package com.llibron.projectplan.dtos.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class TaskEntityDto {

    private Long id;

    private String name;

    private int duration;

    private List<Long> dependencies;

    private LocalDate startDate;

    private LocalDate endDate;
}
