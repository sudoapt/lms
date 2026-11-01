package org.example.lms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.lms.dto.TutorDto;
import org.example.lms.service.TutorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
