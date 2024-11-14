package com.llibron.projectplan.dtos.requests;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class NewTaskRequest {

    private String name;

    private int duration;

    private final List<Long> dependencies = new ArrayList<>();

}
