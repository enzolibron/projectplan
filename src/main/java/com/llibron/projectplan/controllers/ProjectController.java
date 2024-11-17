package com.llibron.projectplan.controllers;

import com.llibron.projectplan.dtos.entity.ProjectEntityDto;
import com.llibron.projectplan.dtos.requests.NewProjectRequest;
import com.llibron.projectplan.dtos.requests.NewTaskRequest;
import com.llibron.projectplan.dtos.requests.UpdateProjectRequest;
import com.llibron.projectplan.dtos.requests.UpdateTaskRequest;
import com.llibron.projectplan.models.Project;
import com.llibron.projectplan.services.ProjectService;
import com.llibron.projectplan.utilities.mapper.ProjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<ProjectEntityDto> getAllProjects() {

        return projectService.findAll();

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getProjectByid(@PathVariable("id") Long id) {

        ProjectEntityDto project = projectService.findById(id);

        if (project == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(project, HttpStatus.OK);
        }

    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity updateProject(@RequestBody UpdateProjectRequest request, @PathVariable("id") Long id) {

        ProjectEntityDto updatedProject = projectService.updateProject(request, id);

        if (updatedProject != null) {
            return ResponseEntity.ok(updatedProject);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping
    public Project createProject(@RequestBody NewProjectRequest request) {

        Project newProject = ProjectMapper.INSTANCE.newProjectRequestToProject(request);

        return projectService.saveProject(newProject);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteProjectById(@PathVariable("id") Long id) {

        ProjectEntityDto project = projectService.findById(id);

        if (project == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            projectService.delete(id);
            return ResponseEntity.ok("deleted successfully");
        }

    }

    @PostMapping(value = "/{id}/tasks")
    public ResponseEntity createTaskInsideProject(@RequestBody NewTaskRequest request, @PathVariable("id") Long projectId) {

        ProjectEntityDto projectEntityDto = projectService.createTaskInsideProject(request, projectId);

        if (projectEntityDto == null) {
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(projectEntityDto, HttpStatus.CREATED);
        }

    }

    @PatchMapping(value = "/{projectId}/tasks/{taskId}")
    public ResponseEntity updateTaskInsideProject(@RequestBody UpdateTaskRequest request, @PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId) {

        ProjectEntityDto projectEntityDto = projectService.updateTaskInsideProject(request, projectId, taskId);

        if (projectEntityDto == null) {
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(projectEntityDto, HttpStatus.CREATED);
        }

    }

    @DeleteMapping(value = "/{projectId}/tasks/{taskId}")
    public ResponseEntity deleteTaskInsideProject(@PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId) {

        projectService.deleteTaskInsideProject(projectId, taskId);

        return ResponseEntity.ok("deleted successfully");
    }

    @DeleteMapping(value = "/{id}/tasks")
    public ResponseEntity deleteAllTaskInsideProject(@PathVariable("id") Long id) {

        ProjectEntityDto projectEntityDto = projectService.deleteAllTaskInsideProject(id);

        if (projectEntityDto == null) {
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
        } else {
            return ResponseEntity.ok("deleted successfully");
        }

    }
}
