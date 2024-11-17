package com.llibron.projectplan.services;

import com.llibron.projectplan.dtos.entity.ProjectEntityDto;
import com.llibron.projectplan.dtos.requests.NewTaskRequest;
import com.llibron.projectplan.dtos.requests.UpdateProjectRequest;
import com.llibron.projectplan.dtos.requests.UpdateTaskRequest;
import com.llibron.projectplan.models.Project;

import java.util.List;

public interface ProjectService {

    Project saveProject(Project project);

    ProjectEntityDto updateProject(UpdateProjectRequest updateProjectRequest, Long projectId);

    List<ProjectEntityDto> findAll();

    ProjectEntityDto findById(Long id);

    void delete(Long id);

    ProjectEntityDto createTaskInsideProject(NewTaskRequest request, Long projectId);

    void deleteTaskInsideProject(Long projectId, Long taskId);

    ProjectEntityDto deleteAllTaskInsideProject(Long projectId);

    ProjectEntityDto updateTaskInsideProject(UpdateTaskRequest request, Long projectId, Long taskId);
}
