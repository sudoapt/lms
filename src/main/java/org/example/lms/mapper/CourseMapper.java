package org.example.lms.mapper;

import org.example.lms.dto.CourseDto;
import org.example.lms.model.Course;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CourseMapper {
    CourseDto toCourseDto(Course course);

    Course toCourseEntity(CourseDto courseDto);
}
