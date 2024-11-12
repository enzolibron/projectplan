package com.llibron.projectplan.utilities.mapper;

import com.llibron.projectplan.dtos.requests.NewProjectRequest;
import com.llibron.projectplan.dtos.responses.ProjectResponse;
import com.llibron.projectplan.models.Project;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

@Mapper(imports = {LocalDate.class})
public interface ProjectMapper {

    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    Project newProjectRequestProject(NewProjectRequest newProjectRequest);

    ProjectResponse projectToProjectResponse(Project project);
}
