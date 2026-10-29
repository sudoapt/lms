package org.example.lms.specification;

import jakarta.persistence.criteria.Predicate;
import org.example.lms.dto.StudentFilterDto;
import org.example.lms.model.Student;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class StudentSpecification {

    public static Specification<Student> studentByFilter(StudentFilterDto studentFilterDto) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (studentFilterDto.studentFilterFirstName() != null && !studentFilterDto.studentFilterFirstName().isBlank()) {
                predicates.add(cb.like(root.get("firstName"), "%" + studentFilterDto.studentFilterFirstName() + "%"));
            }
            if (studentFilterDto.studentFilerLastName() != null && !studentFilterDto.studentFilerLastName().isBlank()) {
                predicates.add(cb.like(root.get("lastName"), "%" + studentFilterDto.studentFilerLastName() + "%"));
            }
            if (studentFilterDto.groupsIDs() != null && !studentFilterDto.groupsIDs().isEmpty()) {
                predicates.add(root.join("groups").get("id").in(studentFilterDto.groupsIDs()));
            }
            if (studentFilterDto.coursesIDs() != null && !studentFilterDto.coursesIDs().isEmpty()) {
                predicates.add(root.join("courses").get("id").in(studentFilterDto.coursesIDs()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
