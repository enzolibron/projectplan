package com.llibron.projectplan.services;

import com.llibron.projectplan.dtos.entity.ProjectEntityDto;
import com.llibron.projectplan.dtos.entity.TaskEntityDto;
import com.llibron.projectplan.dtos.requests.NewTaskRequest;
import com.llibron.projectplan.dtos.requests.UpdateTaskRequest;
import com.llibron.projectplan.exceptions.custom.InvalidTaskRequestException;
import com.llibron.projectplan.exceptions.custom.ResourceNotFoundException;
import com.llibron.projectplan.models.Project;
import com.llibron.projectplan.models.Task;
import com.llibron.projectplan.repositories.ProjectRepository;
import com.llibron.projectplan.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    private Project project;

    private Task task;
    private Task task2;
    private Task task3;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);


        project = new Project();
        project.setId(1L);
        project.setName("Test Project One");

        LocalDate date = LocalDate.parse("11-18-2024", formatter);
        project.setStartDate(date);
        project.setEndDate(date);
        project.setTasks(new ArrayList<>());


        task = new Task();
        task.setName("Test Task One");
        task.setId(1L);
        task.setDuration(1);
        task.setDependencies(new ArrayList<>());

        task2 = new Task();
        task2.setName("Test Task Two");
        task2.setId(2L);
        task2.setDuration(1);

        task3 = new Task();
        task3.setName("Test Task Three");
        task3.setId(3L);
        task3.setDuration(1);

    }

    @Test
    void givenValidProject_whenSaveProject_thenSaveAndReturnProject() {
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        Project createdProject = projectService.saveProject(project);
        assertEquals(project.getName(), createdProject.getName());
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    void givenValidTaskWithNoDependency_whenCreateTaskInsideProject_thenSaveAndReturnProjectEntityDto() {
        NewTaskRequest request = new NewTaskRequest();
        request.setName("Test Task One");
        request.setDuration(1);

        project.getTasks().add(task);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        ProjectEntityDto createdProject = projectService.createTaskInsideProject(request, 1L);

        assertEquals(request.getName(), createdProject.getTasks().get(0).getName());
        assertEquals(request.getDuration(), createdProject.getTasks().get(0).getDuration());

        LocalDate estimatedEndDate = createdProject.getTasks().get(0).getEndDate();
        assertEquals(createdProject.getEndDate(), estimatedEndDate);

        verify(projectRepository, times(1)).findById(1L);
        verify(projectRepository, times(1)).save(project);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void givenValidTaskWithDependency_whenCreateTaskInsideProject_thenSaveAndReturnProjectEntityDto() {
        NewTaskRequest request = new NewTaskRequest();
        request.setName("Test Task Two");
        request.setDuration(1);
        request.getDependencies().add(1L);

        project.getTasks().add(task);
        task2.setDependencies(new ArrayList<>(List.of(1L)));

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(taskRepository.save(any(Task.class))).thenReturn(task2);
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        when(taskRepository.findById(2L)).thenReturn(Optional.of(task2));


        ProjectEntityDto createdProject = projectService.createTaskInsideProject(request, 1L);

        TaskEntityDto createdTask = createdProject.getTasks().stream().filter(task -> task.getName().equals(request.getName())).findAny().orElse(null);

        assertNotNull(createdTask);
        assertEquals(request.getDependencies(), createdTask.getDependencies());

        verify(projectRepository, times(1)).findById(1L);
        verify(projectRepository, times(1)).save(project);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void givenRequestWithInvalidDependency_whenCreateTaskInsideProject_throwInvalidTaskRequestException() {
        NewTaskRequest request = new NewTaskRequest();
        request.setName("Test Task Two");
        request.setDuration(1);
        request.getDependencies().add(3L);

        project.getTasks().add(task);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        InvalidTaskRequestException exception = assertThrows(InvalidTaskRequestException.class, () -> {
            projectService.createTaskInsideProject(request, 1L);
        });

        assertEquals(exception.getMessage(), "Invalid task dependencies. Dependencies in request.dependencies might not exist inside the project");

    }

    @Test()
    void givenInvalidTaskWithInvalidProject_whenCreateTaskInsideProject_throwResourceNotFoundException() {
        NewTaskRequest request = new NewTaskRequest();
        request.setName("Test Task Two");
        request.setDuration(1);
        request.getDependencies().add(2L);

        project.getTasks().add(task);

        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            projectService.createTaskInsideProject(request, 1L);
        });

        assertEquals(exception.getMessage(), "project with id: 1 not found");

    }

    @Test()
    void givenValidTaskWithValidUpdateTaskRequest_whenUpdateTaskInsideProject_thenSaveAndReturnProjectEntityDto() {
        UpdateTaskRequest request = new UpdateTaskRequest();
        request.setName("Test Task One v2.0");
        request.setDuration(2);


        project.getTasks().add(task);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        ProjectEntityDto createdProject = projectService.updateTaskInsideProject(request, 1L, 1L);

        assertEquals(request.getName(), createdProject.getTasks().get(0).getName());
        assertEquals(request.getDuration(), createdProject.getTasks().get(0).getDuration());

        LocalDate estimatedEndDate = createdProject.getTasks().get(0).getEndDate();
        assertEquals(createdProject.getEndDate(), estimatedEndDate);

        verify(projectRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(any(Task.class));
        verify(projectRepository, times(1)).save(project);

    }

    @Test()
    void givenInvalidTaskWithInvalidTaskDependencies_whenUpdateTaskInsideProject_throwInvalidTaskRequestException() {
        UpdateTaskRequest request = new UpdateTaskRequest();
        request.setName("Test Task One v2.0");
        request.setDuration(2);
        request.setDependencies(new ArrayList<>(List.of(2L)));

        project.getTasks().add(task);
        project.getTasks().add(task2);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        InvalidTaskRequestException exception = assertThrows(InvalidTaskRequestException.class, () -> {
            projectService.updateTaskInsideProject(request, 1L, 2L);
        });

        assertEquals(exception.getMessage(), "Invalid task dependencies");
    }

    @Test()
    void givenInvalidTaskWithInvalidTaskDependencies2_whenUpdateTaskInsideProject_throwInvalidTaskRequestException() {
        UpdateTaskRequest request = new UpdateTaskRequest();
        request.setName("Test Task One v2.0");
        request.setDuration(2);
        request.setDependencies(new ArrayList<>(List.of(1L,3L)));

        task.setDependencies(new ArrayList<>());
        task2.setDependencies(new ArrayList<>(List.of(1L)));
        task3.setDependencies(new ArrayList<>(List.of(1L)));
        project.getTasks().add(task);
        project.getTasks().add(task2);
        project.getTasks().add(task3);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        InvalidTaskRequestException exception = assertThrows(InvalidTaskRequestException.class, () -> {
            projectService.updateTaskInsideProject(request, 1L, 3L);
        });

        assertEquals(exception.getMessage(), "Invalid task dependencies");
    }

    @Test()
    void givenInvalidTaskWithDependenciesThatDoesNotExistInsideTheProject_whenUpdateTaskInsideProject_throwInvalidTaskRequestException() {
        UpdateTaskRequest request = new UpdateTaskRequest();
        request.setName("Test Task One v2.0");
        request.setDuration(2);
        request.setDependencies(new ArrayList<>(List.of(2L)));

        task.setDependencies(new ArrayList<>());
        project.getTasks().add(task);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        InvalidTaskRequestException exception = assertThrows(InvalidTaskRequestException.class, () -> {
            projectService.updateTaskInsideProject(request, 1L, 1L);
        });

        assertEquals(exception.getMessage(), "Invalid task dependencies. Dependencies in request.dependencies might not exist inside the project");
    }


    @Test()
    void givenTaskThatDoesNotExist_whenUpdateTaskInsideProject_throwResourceNotFoundException() {
        UpdateTaskRequest request = new UpdateTaskRequest();
        request.setName("Test Task One v2.0");
        request.setDuration(2);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            projectService.updateTaskInsideProject(request, 1L, 1L);
        });

        assertEquals(exception.getMessage(), "task with id: 1 not found");
    }


}