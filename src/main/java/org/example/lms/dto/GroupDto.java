package org.example.lms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.example.lms.model.Student;

import java.util.Set;

public record GroupDto(
        @NotBlank(message = "Empty field is not allowed")
        @Size(min = 1)
        String groupName
//        Set<StudentDto> students
) {
}
