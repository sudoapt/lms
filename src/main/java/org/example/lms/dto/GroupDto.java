package org.example.lms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record GroupDto(
        @NotBlank(message = "Empty field is not allowed")
        @Size(min = 1)
        String groupName
) {
}
