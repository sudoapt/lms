package org.example.lms.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record ScheduleRequestDto(
        @NotNull(message = "Tutor ID is required")
        @Positive(message = "Tutor ID must be positive")
        Long tutorId,

        @NotNull(message = "Course ID is required")
        @Positive(message = "Course ID must be positive")
        Long courseId,

        @NotNull(message = "Group ID is required")
        @Positive(message = "Group ID must be positive")
        Long groupId,

        @NotNull(message = "Start date is required")
        LocalDate startDate,

        @NotNull(message = "End date is required")
        LocalDate endDate
) {}
