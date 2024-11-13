package com.llibron.projectplan.dtos.requests;

import lombok.Getter;

@Getter
public class NewTaskRequest {

    private String name;

    private int duration;

    private final int[] dependencies = new int[0];

}
