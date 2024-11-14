package com.llibron.projectplan.controllers;

import com.llibron.projectplan.dtos.entity.ProjectEntityDto;
import com.llibron.projectplan.dtos.entity.TaskEntityDto;
import com.llibron.projectplan.dtos.requests.NewProjectRequest;
import com.llibron.projectplan.dtos.requests.NewTaskRequest;
import com.llibron.projectplan.models.Project;
import com.llibron.projectplan.services.ProjectService;
import com.llibron.projectplan.services.TaskService;
import com.llibron.projectplan.utilities.mapper.ProjectMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final TaskService taskService;

    public ProjectController(ProjectService projectService, TaskService taskService) {
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @GetMapping
    public List<ProjectEntityDto> getAllProjects() {

        return projectService.findAll();

    }

    @GetMapping(value = "/{id}")
    public ProjectEntityDto getAllProjects(@PathVariable("id") Long id) {

        return projectService.findById(id);

    }

    @PostMapping
    public Project createProject(@RequestBody NewProjectRequest request) {

        Project newProject = ProjectMapper.INSTANCE.newProjectRequestToProject(request);

        return projectService.save(newProject);

    }

    @PostMapping(value = "/{id}/tasks")
    public TaskEntityDto createTaskInsideProject(@RequestBody NewTaskRequest request, @PathVariable("id") Long projectId) {

        return taskService.createTaskInsideProject(request, projectId);

    }
}
