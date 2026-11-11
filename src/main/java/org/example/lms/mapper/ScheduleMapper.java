package org.example.lms.mapper;

import org.example.lms.dto.ScheduleRequestDto;
import org.example.lms.dto.ScheduleResponseDto;
import org.example.lms.model.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {TutorMapper.class, GroupMapper.class, StudentMapper.class, CourseMapper.class})
public interface ScheduleMapper {
    @Mapping(source = "tutor.firstName", target = "tutorFirstName")
    @Mapping(source = "tutor.lastName", target = "tutorLastName")
    @Mapping(source = "course.courseName", target = "courseName")
    @Mapping(source = "group.groupName", target = "groupName")
    ScheduleResponseDto toDto(Schedule schedule);

    @Mapping(target = "id", ignore = true)
    Schedule toScheduleEntity(ScheduleRequestDto scheduleRequestDto);

}
