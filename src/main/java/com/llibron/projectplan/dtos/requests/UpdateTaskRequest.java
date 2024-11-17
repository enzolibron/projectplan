package com.llibron.projectplan.dtos.requests;

import jakarta.validation.constraints.Min;
import lombok.Getter;

import java.util.List;

@Getter
public class UpdateTaskRequest {

    private String name;

    @Min(value = 1, message = "The duration must be equal or more than 1 day")
    private Integer duration;

    private List<Long> dependencies;

}
