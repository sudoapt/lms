package org.example.lms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.lms.dto.TutorDto;
import org.example.lms.service.TutorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/tutor")
@Tag(name = "Tutors", description = "Tutor management")
public class TutorController {
    private final TutorService tutorService;

    @PostMapping
    @Operation(summary = "Create a new tutor")
    public ResponseEntity<TutorDto> createTutor(@Valid @RequestBody TutorDto tutorDto) {
        TutorDto newTutor = tutorService.createTutor(tutorDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTutor);
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
