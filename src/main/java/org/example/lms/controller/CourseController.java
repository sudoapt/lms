package org.example.lms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.lms.dto.CourseDto;
import org.example.lms.repository.CourseRepository;
import org.example.lms.service.CourseService;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/courses")
@Tag(name = "Courses", description = "Course management")
public class CourseController {
    private final CourseService courseService;
    private final CourseRepository courseRepository;

    @PostMapping
    @Operation(summary = "Create a new course")
    public ResponseEntity<CourseDto> createCourse(
            @Valid
            @RequestBody CourseDto courseDto){
        CourseDto newCourse = courseService.createCourse(courseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCourse);
    }

    @GetMapping()
    @PageableAsQueryParam
    @Operation(summary = "Get all the courses")
    public ResponseEntity<Page<CourseDto>> getCourse(
    ) {
        Pageable pageable = PageRequest.of(0, 2, Sort.by("id").ascending());
        Page<CourseDto> courseDtos = courseService.getCourses(pageable);
        return ResponseEntity.ok(courseDtos);
    }

    @PatchMapping("/{courseId}")
    @Operation(summary = "Edit a course")
    public ResponseEntity<CourseDto> editCourse(
            @PathVariable Long courseId,
            @RequestBody CourseDto courseDto
    ) {
        CourseDto editedCourse = courseService.editCourse(courseId, courseDto);
        return ResponseEntity.ok(editedCourse);
    }

    @DeleteMapping("/{courseId}")
    @Operation(summary = "Delete a course")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
