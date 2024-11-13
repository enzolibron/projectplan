package com.llibron.projectplan.services;

import com.llibron.projectplan.models.Project;

import java.util.List;

public interface ProjectService {

    Project save(Project project);

    List<Project> findAll();

    Project findById(Long id);

    void delete(Long id);

}
