package com.llibron.projectplan.interactors;

import com.llibron.projectplan.dtos.requests.NewTaskRequest;
import com.llibron.projectplan.models.Task;

public interface ProjectInteractor {

    Task createTask(NewTaskRequest request, Long projectId);
}
