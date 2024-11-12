package com.llibron.projectplan.services;

import com.llibron.projectplan.models.Project;
import com.llibron.projectplan.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Project> findAll() {
        return (List<Project>) projectRepository.findAll();
    }

    @Override
    public Project findById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
