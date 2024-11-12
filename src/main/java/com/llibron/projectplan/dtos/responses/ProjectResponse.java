package com.llibron.projectplan.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.llibron.projectplan.models.Task;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ProjectResponse {

    private Long id;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    private LocalDate endDate;
    private List<Task> tasks;

}
