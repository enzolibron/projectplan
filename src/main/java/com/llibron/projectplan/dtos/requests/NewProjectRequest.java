package com.llibron.projectplan.dtos.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.llibron.projectplan.models.Task;
import lombok.Getter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
public class NewProjectRequest {

    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    private LocalDate startDate;

    private final List<Task> tasks = new ArrayList<>();
}
