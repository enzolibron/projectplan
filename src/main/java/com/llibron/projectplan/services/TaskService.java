package com.llibron.projectplan.services;

import com.llibron.projectplan.dtos.entity.TaskEntityDto;
import com.llibron.projectplan.dtos.requests.NewTaskRequest;

public interface TaskService {

    TaskEntityDto createTaskInsideProject(NewTaskRequest request, Long projectId);

    TaskEntityDto findById(Long id);
}
