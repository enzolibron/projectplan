package com.llibron.projectplan.utilities.mapper;

import com.llibron.projectplan.dtos.requests.NewProjectRequest;
import com.llibron.projectplan.models.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProjectMapper {

    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    @Mapping(source = "startDate", target = "endDate")
    @Mapping(source = "startDate", target = "startDate")
    Project newProjectRequestToProject(NewProjectRequest newProjectRequest);

}
