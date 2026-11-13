package org.example.lms.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.lms.dto.CourseDto;
import org.example.lms.exception.AlreadyExistsException;
import org.example.lms.mapper.CourseMapper;
import org.example.lms.model.Course;
import org.example.lms.repository.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Transactional
    public CourseDto createCourse(CourseDto courseDto) {
        if (courseRepository.existsByCourseName(courseDto.courseName())) {

            throw new AlreadyExistsException("Course name " + courseDto.courseName() + " is already taken");
        }

        Course course = courseMapper.toCourseEntity(courseDto);
        Course newCourse = courseRepository.save(course); // SDJPA executes insert/update (IS THAT A MUST?)
        return courseMapper.toCourseDto(newCourse);
    }

    @Transactional
    public CourseDto editCourse(Long courseId, CourseDto courseDto) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("No course with the id:" + courseId + " found"));

        if (courseDto.courseName() != null) {
            course.setCourseName(courseDto.courseName());
        }

        if (courseDto.courseDescription() != null) {
            course.setCourseName(courseDto.courseDescription());
        }

        return courseMapper.toCourseDto(course);

    }

    @Transactional
    public void deleteCourse(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new EntityNotFoundException("No course with the id: " + courseId + " found");
        }
        courseRepository.existsById(courseId);
    }

    @Transactional(readOnly = true)
    public Page<CourseDto> getCourses(Pageable pageable) {
        Page<Course> coursesPage = courseRepository.findAll(pageable);
        return coursesPage.map(courseMapper::toCourseDto);
    }
}
