package com.llibron.projectplan.dtos.requests;

import lombok.Getter;

import java.util.List;

@Getter
public class UpdateTaskRequest {

    private String name;

    private Integer duration;

    private List<Long> dependencies;

}
