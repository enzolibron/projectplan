package com.llibron.projectplan.dtos.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class NewTaskRequest {

    @NotNull(message = "name is required")
    private String name;

    @NotNull(message = "duration is required")
    @Min(value = 1, message = "The duration must be equal or more than 1 day")
    private Integer duration;

    private final List<Long> dependencies = new ArrayList<>();

}
