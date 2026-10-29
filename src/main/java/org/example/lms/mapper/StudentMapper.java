package org.example.lms.mapper;

import org.example.lms.dto.StudentDto;
import org.example.lms.model.Group;
import org.example.lms.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudentMapper {

    // receive Group entities, return StudentDto.groupIds Set<Long>
    @Mapping(target = "groupIDs", source = "groups")
    StudentDto toStudentDto(Student student);
    @Mapping(target = "groups", ignore = true)
    Student toStudentEntity(StudentDto studentDto);

    // method takes full Group entities and returns only their IDs
    default Set<Long> mapGroupsToIds(Set<Group> groups) {
        if (groups == null) {
            return Set.of(); // null check. if nothing is found return an empty set
        }

        return groups.stream()
                .map(Group::getId)
                .collect(Collectors.toSet());
    }
}
