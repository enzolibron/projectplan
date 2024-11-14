package com.llibron.projectplan.services;

import com.llibron.projectplan.dtos.entity.TaskEntityDto;
import com.llibron.projectplan.dtos.requests.NewTaskRequest;
import com.llibron.projectplan.models.Project;
import com.llibron.projectplan.models.Task;
import com.llibron.projectplan.repositories.ProjectRepository;
import com.llibron.projectplan.repositories.TaskRepository;
import com.llibron.projectplan.utilities.mapper.TaskMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final ProjectRepository projectRepository;

    public TaskServiceImpl(TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    @Transactional
    @Override
    public TaskEntityDto createTaskInsideProject(NewTaskRequest request, Long projectId) {

        //check if dependency doesn't loop
        for (Long l : request.getDependencies()) {
            if (Objects.equals(l, projectId)) {
                //TODO: throw bad request
            }
        }

        List<TaskEntityDto> newTaskDependencies = request.getDependencies()
                .stream()
                .map(this::findById)
                .toList();


        newTaskDependencies.forEach(dependency ->{
            if(dependency.getDependencies().contains(projectId)){
                //TODO: throw bad request
            }
        });

        Task newTask = new Task();
        newTask.setDuration(request.getDuration());
        newTask.setName(request.getName());
        newTask.setDependencies(request.getDependencies());

        //update project tasks
        Optional<Project> project = projectRepository.findById(projectId);

        if (project.isPresent()) {

            newTask.setProject(project.get());
            Task savedTask = taskRepository.save(newTask);

            List<Task> projectTasks = project.get().getTasks();
            projectTasks.add(savedTask);
            project.get().setTasks(projectTasks);

            projectRepository.save(project.get());

            return TaskMapper.INSTANCE.taskToTaskEntityDTO(savedTask);
        }

        return TaskMapper.INSTANCE.taskToTaskEntityDTO(taskRepository.save(newTask));

    }

    @Override
    public TaskEntityDto findById(Long id) {
        Task task = taskRepository.findById(id).orElseGet(null);
        if(task == null) {
            return null;
        } else {
            return TaskMapper.INSTANCE.taskToTaskEntityDTO(task);
        }
    }
}
