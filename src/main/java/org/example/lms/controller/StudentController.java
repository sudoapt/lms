package org.example.lms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.lms.dto.StudentDto;
import org.example.lms.dto.StudentFilterDto;
import org.example.lms.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/students")
@Tag(name = "Students", description = "Student management")
public class StudentController {
    private final StudentService studentService;

    @PostMapping("/search")
    @Operation(summary = "Get student by filter")
    public ResponseEntity<Page<StudentDto>> getStudentsByFilter(@RequestBody(required = false) StudentFilterDto studentFilterDto,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "2") int size) {
        // fallback for an empty search (no request body)
        StudentFilterDto filterDto = (studentFilterDto !=null) ? studentFilterDto : new StudentFilterDto(null, null, List.of(), List.of());

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<StudentDto> filteredStudents = studentService.filterStudents(filterDto, pageable);
        return ResponseEntity.ok(filteredStudents); // TODO: handle empty search
    }

    @PostMapping
    @Operation(summary = "Create a new student")
    public ResponseEntity<StudentDto> createUser(@Valid @RequestBody StudentDto studentDto) {
        StudentDto createdStudent = studentService.createStudent(studentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    @PutMapping("/{studentId}/groups/{groupId}")
    @Operation(summary = "Add a student to a group")
    public ResponseEntity<Void> addStudentToGroup(
            @PathVariable Long studentId,
            @PathVariable Long groupId
    ) {
        studentService.addStudentToGroup(studentId, groupId);
        return ResponseEntity.noContent().build();
    }
}
