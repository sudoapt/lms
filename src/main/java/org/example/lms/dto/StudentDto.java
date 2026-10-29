package org.example.lms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record StudentDto(
        @NotBlank(message = "Empty field is not allowed")
        @Size(min = 1)
        String firstName,
        @NotBlank(message = "Empty field is not allowed")
        @Size(min = 1)
        String lastName,

        @NotEmpty(message = "Each student has to be associated with at least one group")
        Set<Long> groupIDs
) {
}
