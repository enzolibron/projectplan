package com.llibron.projectplan.dtos.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.llibron.projectplan.models.Task;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class ProjectEntityDto {

    private Long id;
    private String name;
    private List<Task> tasks;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    private LocalDate endDate;
}
