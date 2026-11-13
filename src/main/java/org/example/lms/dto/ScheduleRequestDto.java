package org.example.lms.dto;

import java.time.LocalDate;

public record ScheduleRequestDto(
        Long tutorId,
        Long courseId,
        Long groupId,
        LocalDate startDate,
        LocalDate endDate
) {}
