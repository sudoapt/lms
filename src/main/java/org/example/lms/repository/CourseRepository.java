package org.example.lms.repository;

import jakarta.persistence.EntityNotFoundException;
import org.example.lms.dto.CourseDto;
import org.example.lms.model.Course;
import org.example.lms.model.Tutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsByCourseName(String courseName);

}
