package com.llibron.projectplan.dtos.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class NewProjectRequest {

    @NotNull(message = "name is required")
    private String name;

    @NotNull(message = "startDate is required")
    @Pattern(regexp = "\\d{2}-\\d{2}-\\d{4}", message = "startDate must be in the format MM-dd-yyyy")
    private String startDate;

}
