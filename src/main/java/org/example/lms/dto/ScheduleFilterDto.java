package org.example.lms.dto;

import java.time.LocalDate;

public record ScheduleFilterDto(
        Long id,
        String tutorFirstName,
        String tutorLastName,
        String courseName,
        String groupName,
        LocalDate startDate,
        LocalDate endDate
) {
}
