package com.llibron.projectplan.utilities.mapper;

import com.llibron.projectplan.dtos.entity.ProjectEntityDto;
import com.llibron.projectplan.dtos.requests.NewProjectRequest;
import com.llibron.projectplan.models.Project;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProjectMapper {

    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    Project newProjectRequestToProject(NewProjectRequest newProjectRequest);

    ProjectEntityDto projectToProjectEntityDto(Project project);

}
