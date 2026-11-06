package org.example.lms.mapper;

import org.example.lms.dto.ScheduleDto;
import org.example.lms.model.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {TutorMapper.class, GroupMapper.class, StudentMapper.class, CourseMapper.class})
public interface ScheduleMapper {
//    ScheduleMapper INSTANCE = Mappers.getMapper(ScheduleMapper.class);
    @Mapping(source = "tutor.id", target = "tutorId")
    @Mapping(source = "tutor.firstName", target = "tutorFirstName")
    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "course.title", target = "courseTitle")
    @Mapping(source = "group.id", target = "groupId")
    @Mapping(source = "group.name", target = "groupName")
    ScheduleDto toDto(Schedule schedule);


    @Mapping(source = "tutorId", target = "tutor.id")
    @Mapping(source = "courseId", target = "course.id")
    @Mapping(source = "groupId", target = "group.id")
    @Mapping(target = "id", ignore = true)
    Schedule toScheduleEntity(ScheduleDto scheduleDto);

}
