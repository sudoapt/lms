package org.example.lms.mapper;


import org.example.lms.dto.GroupDto;
import org.example.lms.model.Group;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GroupMapper {
    GroupDto toGroupDto(Group group);

    Group toGroupEntity(GroupDto groupDto);
}
