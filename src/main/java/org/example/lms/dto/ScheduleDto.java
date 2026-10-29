package org.example.lms.dto;

import java.time.LocalDateTime;

public record ScheduleDto(
        Long id,
        Long tutorId,
        String tutorFirstName,
        String tutorLastName,
        Long courseId,
        String courseTitle,
        Long groupId,
        String groupName,
        LocalDateTime startDate,
        LocalDateTime endDate) {
}
