package com.llibron.projectplan.dtos.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class TaskEntityDto {

    private Long id;

    private String name;

    private List<Long> dependencies;

    private Long projectId;

    private LocalDate startDate;

    private LocalDate endDate;
}
