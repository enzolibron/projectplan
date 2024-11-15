package com.llibron.projectplan.services;

import com.llibron.projectplan.dtos.entity.ProjectEntityDto;
import com.llibron.projectplan.dtos.requests.NewTaskRequest;
import com.llibron.projectplan.models.Project;

import java.util.List;

public interface ProjectService {

    Project save(Project project);

    List<ProjectEntityDto> findAll();

    ProjectEntityDto findById(Long id);

    void delete(Long id);

    ProjectEntityDto createTaskInsideProject(NewTaskRequest request, Long projectId);

    ProjectEntityDto deleteAllTaskInsideProject(Long projectId);

    ProjectEntityDto updateTaskInsideProject(NewTaskRequest request, Long projectId, Long taskId);
}
