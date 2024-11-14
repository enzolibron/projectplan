package com.llibron.projectplan.utilities.mapper;

import com.llibron.projectplan.dtos.entity.TaskEntityDto;
import com.llibron.projectplan.models.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskMapper {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    @Mapping(source = "project.id", target = "projectId")
    TaskEntityDto taskToTaskEntityDTO(Task task);

}
