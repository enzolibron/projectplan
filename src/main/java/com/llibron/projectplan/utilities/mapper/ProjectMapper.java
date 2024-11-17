package com.llibron.projectplan.utilities.mapper;

import com.llibron.projectplan.dtos.requests.NewProjectRequest;
import com.llibron.projectplan.dtos.requests.UpdateProjectRequest;
import com.llibron.projectplan.models.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ProjectMapper {

    @Autowired
    DateMapper dateMapper;

    @Mapping(target = "startDate", expression = "java(dateMapper.asLocalDate(newProjectRequest.getStartDate()))")
    @Mapping(target = "endDate", expression = "java(dateMapper.asLocalDate(newProjectRequest.getStartDate()))")
    public abstract Project newProjectRequestToProject(NewProjectRequest newProjectRequest);

    @Mapping(target = "startDate", expression = "java(dateMapper.asLocalDate(updateProjectRequest.getStartDate()))")
    public abstract Project updateProjectRequestToProject(UpdateProjectRequest updateProjectRequest);

}
