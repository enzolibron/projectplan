package com.llibron.projectplan.controllers;

import com.llibron.projectplan.dtos.entity.ProjectEntityDto;
import com.llibron.projectplan.dtos.requests.NewProjectRequest;
import com.llibron.projectplan.dtos.requests.NewTaskRequest;
import com.llibron.projectplan.dtos.requests.UpdateProjectRequest;
import com.llibron.projectplan.dtos.requests.UpdateTaskRequest;
import com.llibron.projectplan.models.Project;
import com.llibron.projectplan.services.ProjectService;
import com.llibron.projectplan.utilities.mapper.ProjectMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectMapper projectMapper;
    private final ProjectService projectService;

    public ProjectController(ProjectMapper projectMapper, ProjectService projectService) {
        this.projectMapper = projectMapper;
        this.projectService = projectService;
    }

    @GetMapping
    public List<ProjectEntityDto> getAllProjects() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            System.out.println(userDetails.getUsername());
        }
        return projectService.findAll();

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getProjectByid(@PathVariable("id") Long id) {

        ProjectEntityDto project = projectService.findById(id);

        return ResponseEntity.ok(project);

    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<?> updateProject(@Valid @RequestBody UpdateProjectRequest request, @PathVariable("id") Long id) {

        Project updateProjectRequest = projectMapper.updateProjectRequestToProject(request);
        ProjectEntityDto updatedProject = projectService.updateProject(updateProjectRequest, id);

        return ResponseEntity.ok(updatedProject);

    }

    @PostMapping
    public ResponseEntity<?> createProject(@Valid @RequestBody NewProjectRequest request) {

        Project newProject = projectMapper.newProjectRequestToProject(request);
        newProject.setTasks(new ArrayList<>());

        return new ResponseEntity<>(projectService.saveProject(newProject), HttpStatus.CREATED);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteProjectById(@PathVariable("id") Long id) {

        projectService.deleteProjectById(id);
        return ResponseEntity.ok("deleted successfully");

    }

    @PostMapping(value = "/{id}/tasks")
    public ResponseEntity<?> createTaskInsideProject(@Valid @RequestBody NewTaskRequest request, @PathVariable("id") Long projectId) {

        ProjectEntityDto projectEntityDto = projectService.createTaskInsideProject(request, projectId);

        return new ResponseEntity<>(projectEntityDto, HttpStatus.CREATED);

    }

    @PatchMapping(value = "/{projectId}/tasks/{taskId}")
    public ResponseEntity<?> updateTaskInsideProject(@Valid @RequestBody UpdateTaskRequest request, @PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId) {

        ProjectEntityDto projectEntityDto = projectService.updateTaskInsideProject(request, projectId, taskId);

        return new ResponseEntity<>(projectEntityDto, HttpStatus.CREATED);

    }

    @DeleteMapping(value = "/{projectId}/tasks/{taskId}")
    public ResponseEntity<?> deleteTaskInsideProject(@PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId) {

        projectService.deleteTaskInsideProject(projectId, taskId);

        return ResponseEntity.ok("deleted successfully");
    }

    @DeleteMapping(value = "/{id}/tasks")
    public ResponseEntity<?> deleteAllTaskInsideProject(@PathVariable("id") Long id) {

        projectService.deleteAllTaskInsideProject(id);

        return ResponseEntity.ok("deleted successfully");

    }
}
