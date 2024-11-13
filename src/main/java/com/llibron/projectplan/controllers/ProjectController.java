package com.llibron.projectplan.controllers;

import com.llibron.projectplan.dtos.requests.NewProjectRequest;
import com.llibron.projectplan.dtos.requests.NewTaskRequest;
import com.llibron.projectplan.dtos.responses.ProjectResponse;
import com.llibron.projectplan.interactors.ProjectInteractor;
import com.llibron.projectplan.models.Project;
import com.llibron.projectplan.models.Task;
import com.llibron.projectplan.services.ProjectService;
import com.llibron.projectplan.utilities.mapper.ProjectMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    ProjectService projectService;
    ProjectInteractor projectInteractor;

    public ProjectController(ProjectService projectService, ProjectInteractor projectInteractor) {
        this.projectService = projectService;
        this.projectInteractor = projectInteractor;
    }

    @GetMapping
    public List<ProjectResponse> getAllProjects(){
        return projectService.findAll().stream().map(ProjectMapper.INSTANCE::projectToProjectResponse).collect(Collectors.toList());
    }

    @PostMapping
    public ProjectResponse createProject(@RequestBody NewProjectRequest request){
        Project newProject = ProjectMapper.INSTANCE.newProjectRequestProject(request);

        return ProjectMapper.INSTANCE.projectToProjectResponse(projectService.save(newProject));
    }

    @PostMapping(value = "/{id}/tasks")
    public Task addTask(@RequestBody NewTaskRequest request, @PathVariable("id") Long projectId) {
        return projectInteractor.createTask(request, projectId);
    }
}
