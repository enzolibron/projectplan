package com.llibron.projectplan.interactors;

import com.llibron.projectplan.dtos.requests.NewTaskRequest;
import com.llibron.projectplan.models.Project;
import com.llibron.projectplan.models.Task;
import com.llibron.projectplan.services.ProjectService;
import com.llibron.projectplan.services.TaskService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ProjectInteractorImpl implements ProjectInteractor{

    private final ProjectService projectService;

    private final TaskService taskService;

    public ProjectInteractorImpl(ProjectService projectService, TaskService taskService) {
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @Override
    public Task createTask(NewTaskRequest request, Long projectId) {

        Project project = projectService.findById(projectId);

        List<Task> dependencies = Arrays.stream(request.getDependencies())
                .boxed()
                .toList()
                .stream()
                .map(id -> taskService.findById(Long.valueOf(id)))
                .toList();

        Task newTask = new Task();
        newTask.setDuration(request.getDuration());
        newTask.setName(request.getName());
        newTask.setDependencies(dependencies);

        if (project != null) {

            newTask.setProjectId(projectId);
            Task savedTask = taskService.save(newTask);

            List<Task> projectTasks = project.getTasks();
            projectTasks.add(savedTask);
            project.setTasks(projectTasks);

            projectService.save(project);

            return savedTask;
        }

        return taskService.save(newTask);

    }
}
