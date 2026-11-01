package org.example.lms.service;

import lombok.RequiredArgsConstructor;
import org.example.lms.dto.CourseDto;
import org.example.lms.exception.AlreadyExistsException;
import org.example.lms.mapper.CourseMapper;
import org.example.lms.model.Course;
import org.example.lms.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Transactional
    public CourseDto createCourse(CourseDto courseDto){
        if (courseRepository.existsByCourseName(courseDto.courseName())) {

            throw new AlreadyExistsException("Course name " + courseDto.courseName() + " is already taken");
        }

        Course course = courseMapper.toCourseEntity(courseDto);
        Course newCourse = courseRepository.save(course); // SDJPA executes insert/update
        return courseMapper.toCourseDto(newCourse);
    }
}
