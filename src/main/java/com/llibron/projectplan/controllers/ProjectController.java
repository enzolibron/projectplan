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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    ProjectMapper projectMapper;

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<ProjectEntityDto> getAllProjects() {

        return projectService.findAll();

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getProjectByid(@PathVariable("id") Long id) {

        ProjectEntityDto project = projectService.findById(id);

        if (project == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(project, HttpStatus.OK);
        }

    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<?> updateProject(@Valid @RequestBody UpdateProjectRequest request, @PathVariable("id") Long id) {

        Project updateProjectRequest = projectMapper.updateProjectRequestToProject(request);
        ProjectEntityDto updatedProject = projectService.updateProject(updateProjectRequest, id);

        if (updatedProject != null) {
            return ResponseEntity.ok(updatedProject);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping
    public ResponseEntity<?> createProject(@Valid @RequestBody NewProjectRequest request) {

        Project newProject = projectMapper.newProjectRequestToProject(request);
        newProject.setTasks(new ArrayList<>());

        return new ResponseEntity<>(projectService.saveProject(newProject), HttpStatus.CREATED);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteProjectById(@PathVariable("id") Long id) {

        ProjectEntityDto project = projectService.findById(id);

        if (project == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            projectService.delete(id);
            return ResponseEntity.ok("deleted successfully");
        }

    }

    @PostMapping(value = "/{id}/tasks")
    public ResponseEntity<?> createTaskInsideProject(@Valid @RequestBody NewTaskRequest request, @PathVariable("id") Long projectId) {

        ProjectEntityDto projectEntityDto = projectService.createTaskInsideProject(request, projectId);

        if (projectEntityDto == null) {
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(projectEntityDto, HttpStatus.CREATED);
        }

    }

    @PatchMapping(value = "/{projectId}/tasks/{taskId}")
    public ResponseEntity<?> updateTaskInsideProject(@Valid @RequestBody UpdateTaskRequest request, @PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId) {

        ProjectEntityDto projectEntityDto = projectService.updateTaskInsideProject(request, projectId, taskId);

        if (projectEntityDto == null) {
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(projectEntityDto, HttpStatus.CREATED);
        }

    }

    @DeleteMapping(value = "/{projectId}/tasks/{taskId}")
    public ResponseEntity<?> deleteTaskInsideProject(@PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId) {

        projectService.deleteTaskInsideProject(projectId, taskId);

        return ResponseEntity.ok("deleted successfully");
    }

    @DeleteMapping(value = "/{id}/tasks")
    public ResponseEntity<?> deleteAllTaskInsideProject(@PathVariable("id") Long id) {

        ProjectEntityDto projectEntityDto = projectService.deleteAllTaskInsideProject(id);

        if (projectEntityDto == null) {
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
        } else {
            return ResponseEntity.ok("deleted successfully");
        }

    }
}
