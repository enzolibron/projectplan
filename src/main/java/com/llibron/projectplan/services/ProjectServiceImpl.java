package com.llibron.projectplan.services;

import com.llibron.projectplan.dtos.entity.ProjectEntityDto;
import com.llibron.projectplan.dtos.entity.TaskEntityDto;
import com.llibron.projectplan.models.Project;
import com.llibron.projectplan.repositories.ProjectRepository;
import com.llibron.projectplan.utilities.mapper.TaskMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public List<ProjectEntityDto> findAll() {
        List<Project> projects = (List<Project>) projectRepository.findAll();

        return projects.stream().map(project -> {
            ProjectEntityDto projectEntityDto = new ProjectEntityDto();
            projectEntityDto.setId(project.getId());
            projectEntityDto.setName(project.getName());
            projectEntityDto.setEndDate(project.getEndDate());
            projectEntityDto.setStartDate(project.getStartDate());

            List<TaskEntityDto> tasks = project.getTasks().stream().map(TaskMapper.INSTANCE::taskToTaskEntityDTO).toList();
            projectEntityDto.setTasks(tasks);

            return projectEntityDto;
        }).collect(Collectors.toList());
    }

    @Override
    public Project findById(Long id) {
        return projectRepository.findById(id).orElseGet(null);
    }

    @Override
    public void delete(Long id) {

    }
}
