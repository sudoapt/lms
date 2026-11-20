package org.example.lms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.lms.dto.TutorDto;
import org.example.lms.service.TutorService;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/tutors")
@Tag(name = "Tutors", description = "Tutor management")
public class TutorController {
    private final TutorService tutorService;

    @PostMapping
    @Operation(summary = "Create a new tutor")
    public ResponseEntity<TutorDto> createTutor(@Valid @RequestBody TutorDto tutorDto) {
        TutorDto newTutor = tutorService.createTutor(tutorDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTutor);
    }

    @GetMapping
    @Operation(summary = "Get all the tutors")
    @PageableAsQueryParam
    public ResponseEntity<Page<TutorDto>> getTutors(
            @Parameter(hidden = true)
            @PageableDefault(size = 2, sort = "id")
            Pageable pageable
    ) {
        Page<TutorDto> tutorDtoPage = tutorService.getTutors(pageable);
        return ResponseEntity.ok(tutorDtoPage);
    }

    @PatchMapping("/{tutorId}")
    @Operation(summary = "Edit a tutor")
    public ResponseEntity<TutorDto> editTutor(
            @Valid
            @PathVariable Long tutorId,
            @RequestBody TutorDto tutorDto) {

        TutorDto editedTutor = tutorService.editTutor(tutorId, tutorDto);
        return ResponseEntity.ok(editedTutor);
    }

    @PutMapping("/{tutorId}/courses/{courseId}")
    @Operation(summary = "Assign a tutor to a course")
    public ResponseEntity<Void> assignTutorToCourse(
            @PathVariable Long tutorId,
            @PathVariable Long courseId) {

        tutorService.assignTutorToCourse(tutorId, courseId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{tutorId}")
    @Operation(summary = "Delete a tutor")
    public ResponseEntity<Void> deleteTutor(
            @PathVariable Long tutorId
    ) {
        tutorService.deleteTutor(tutorId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
