package org.example.lms.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ScheduleRequestDto(
        Long tutorId,
        Long courseId,
        Long groupId,
        LocalDate startDate,
        LocalDate endDate
) {}
