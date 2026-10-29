package org.example.lms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TutorDto(
        @NotBlank(message = "Empty field is not allowed")
        @Size(min = 1)
        String firstName,
        @NotBlank(message = "Empty field is not allowed")
        @Size(min = 1)
        String lastName
) {
}
