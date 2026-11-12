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
    /// @return True if no data passed to filter
    public boolean isEmpty() {
        return id == null
                && tutorFirstName == null
                && tutorLastName == null
                && courseName == null
                && groupName == null
                && startDate == null
                && endDate == null;
    }
}
