package com.llibron.projectplan.services;

import com.llibron.projectplan.dtos.entity.ProjectEntityDto;
import com.llibron.projectplan.dtos.entity.TaskEntityDto;
import com.llibron.projectplan.dtos.requests.NewTaskRequest;
import com.llibron.projectplan.models.Project;
import com.llibron.projectplan.models.Task;
import com.llibron.projectplan.repositories.ProjectRepository;
import com.llibron.projectplan.repositories.TaskRepository;
import com.llibron.projectplan.utilities.mapper.TaskMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final TaskRepository taskRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public List<ProjectEntityDto> findAll() {

        List<Project> projects = (List<Project>) projectRepository.findAll();

        return projects
                .stream()
                .map(this::getProjectEntityDto).collect(Collectors.toList());

    }

    @Override
    public ProjectEntityDto findById(Long id) {
        Optional<Project> project = projectRepository.findById(id);

        return project.map(this::getProjectEntityDto).orElse(null);

    }

    @Override
    public void delete(Long id) {
        projectRepository.deleteById(id);
    }

    @Transactional
    @Override
    public ProjectEntityDto createTaskInsideProject(NewTaskRequest request, Long projectId) {

        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isPresent()) {

            List<Task> projectTasks = project.get().getTasks();

            //check if task dependency exist, if not bad request
            HashSet<Long> projectTasksIdSet = new HashSet<>(projectTasks.stream().map(Task::getId).toList());
            if (!projectTasksIdSet.containsAll(request.getDependencies())) {
                return null;
            }

            Task newTask = new Task();
            newTask.setDuration(request.getDuration());
            newTask.setName(request.getName());
            newTask.setDependencies(request.getDependencies());
            newTask.setProject(project.get());

            projectTasks.add(taskRepository.save(newTask));
            project.get().setTasks(projectTasks);

            Project savedProject = projectRepository.save(processProjectTasksSchedule(project.get()));

            return getProjectEntityDto(savedProject);

        } else {
            return null;
        }

    }



    @Transactional
    @Override
    public ProjectEntityDto deleteAllTaskInsideProject(Long projectId) {

        Optional<Project> project = projectRepository.findById(projectId);

        if (project.isPresent()) {
            List<Task> tasks = taskRepository.findByProjectId(projectId);

            taskRepository.deleteAll(tasks);

            project.get().setEndDate(project.get().getStartDate());

            return getProjectEntityDto(projectRepository.save(project.get()));
        } else {
            return null;
        }

    }

    private ProjectEntityDto getProjectEntityDto(Project savedProject) {
        ProjectEntityDto projectEntityDto = new ProjectEntityDto();
        projectEntityDto.setId(savedProject.getId());
        projectEntityDto.setName(savedProject.getName());
        projectEntityDto.setStartDate(savedProject.getStartDate());
        projectEntityDto.setEndDate(savedProject.getEndDate());
        List<TaskEntityDto> taskEntityDtos = savedProject.getTasks().stream().map(TaskMapper.INSTANCE::taskToTaskEntityDto).collect(Collectors.toList());
        projectEntityDto.setTasks(taskEntityDtos);

        return projectEntityDto;
    }

    public Project processProjectTasksSchedule(Project project) {

        if (project.getTasks().isEmpty()) {
            return project;
        }
        LocalDate startDate = project.getStartDate();

        List<Long> uncompletedTaskIds = project.getTasks().stream().map(Task::getId).collect(Collectors.toList());
        List<Task> processedTasks = new ArrayList<>();

        //process all task w/o dependency
        List<Task> projectTasksWithoutDependency = project.getTasks().stream().filter(task -> task.getDependencies().isEmpty()).toList();
        for (Task task : projectTasksWithoutDependency) {

            startDate = setDate(project, startDate, uncompletedTaskIds, processedTasks, task);
        }

        //process all task w/dependency
        List<Task> projectTasksWithDependency = project.getTasks().stream().filter(task -> !task.getDependencies().isEmpty()).toList();
        while (!uncompletedTaskIds.isEmpty()) {
            for (Task task : projectTasksWithDependency) {
                List<Long> processedTaskIds = project.getTasks().stream().map(Task::getId).toList();
                if (new HashSet<>(processedTaskIds).containsAll(task.getDependencies())) {

                    startDate = setDate(project, startDate, uncompletedTaskIds, processedTasks, task);

                }

            }
        }

        project.setTasks(processedTasks);


        return project;

    }

    private LocalDate setDate(Project project, LocalDate startDate, List<Long> uncompletedTaskIds, List<Task> processedTasks, Task task) {

        task.setStartDate(startDate);
        task.setEndDate(startDate.plusDays(task.getDuration() - 1));

        project.setEndDate(task.getEndDate());

        startDate = startDate.plusDays(task.getDuration());

        processedTasks.add(task);

        uncompletedTaskIds.removeIf(taskId -> taskId.equals(task.getId()));

        return startDate;

    }


}
