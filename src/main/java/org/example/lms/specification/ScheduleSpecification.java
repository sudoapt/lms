package org.example.lms.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.example.lms.dto.ScheduleFilterDto;
import org.example.lms.dto.ScheduleResponseDto;
import org.example.lms.model.Course;
import org.example.lms.model.Group;
import org.example.lms.model.Schedule;
import org.example.lms.model.Tutor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ScheduleSpecification {
    public static Specification<Schedule> scheduleByFilter(ScheduleFilterDto scheduleFilterDto) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            // TODO: handle empty field
            if (scheduleFilterDto.id() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), scheduleFilterDto.id()));
            }

            boolean hasFirstName = scheduleFilterDto.tutorFirstName() != null && !scheduleFilterDto.tutorFirstName().isBlank();
            boolean hasLastName = scheduleFilterDto.tutorLastName() != null && !scheduleFilterDto.tutorLastName().isBlank();

            // if neither are provided -- skip the block
            if (hasFirstName || hasLastName) {
                Join<Schedule, Tutor> tutorJoin = root.join("tutor", JoinType.LEFT);
                if (hasFirstName) {
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(tutorJoin.get("firstName")),
                            "%" + scheduleFilterDto.tutorFirstName().toLowerCase() + "%"
                    ));
                }
                if (hasLastName) {
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(tutorJoin.get("lastName")),
                            "%" + scheduleFilterDto.tutorLastName().toLowerCase() + "%"
                    ));
                }
            }

            // 3. Filter by Course Title (Assuming field name in Course entity is 'title')
            if (scheduleFilterDto.courseName() != null && !scheduleFilterDto.courseName().isBlank()) {
                Join<Schedule, Course> courseJoin = root.join("course", JoinType.LEFT);
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(courseJoin.get("courseName")),
                        "%" + scheduleFilterDto.courseName().toLowerCase() + "%"
                ));
            }

            // 4. Filter by Group Name
            if (scheduleFilterDto.groupName() != null && !scheduleFilterDto.groupName().isBlank()) {
                Join<Schedule, Group> groupJoin = root.join("group", JoinType.LEFT);
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(groupJoin.get("groupName")),
                        "%" + scheduleFilterDto.groupName().toLowerCase() + "%"
                ));
            }

            if (scheduleFilterDto.startDate() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("startDate"),
                                scheduleFilterDto.startDate()
                ));
            }
            if (scheduleFilterDto.endDate() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("endDate"),
                        scheduleFilterDto.endDate()
                ));
            }


            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
