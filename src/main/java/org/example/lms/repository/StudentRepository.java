package org.example.lms.repository;

import jakarta.persistence.EntityNotFoundException;
import org.example.lms.dto.StudentFilterDto;
import org.example.lms.model.Student;
import org.example.lms.model.Tutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {
    //    Page<StudentFilterDto> findAll(StudentFilterDto studentFilterDto, Pageable pageable);
    default Student getOrThrow(Long id) {
        return findById(id).orElseThrow(() ->
                new EntityNotFoundException("No student with the id: " + id + " is found"));
    }
}
