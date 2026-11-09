package org.example.lms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.lms.dto.ScheduleRequestDto;
import org.example.lms.dto.ScheduleResponseDto;
import org.example.lms.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/schedule")
@Tag(name = "Schedules", description = "Schedule management")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PutMapping
    @Operation(summary = "Create a schedule")
    public ResponseEntity<ScheduleResponseDto> createSchedule(
            @Valid @RequestBody ScheduleRequestDto scheduleRequestDto) {
        ScheduleResponseDto createSchedule = scheduleService.createSchedule(scheduleRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createSchedule);
    }

    @PutMapping("/{scheduleId}")
    @Operation(summary = "Edit a schedule")
    public ResponseEntity<ScheduleResponseDto> editSchedule(
            @PathVariable Long scheduleId,
            @Valid @RequestBody ScheduleRequestDto scheduleRequestDto
    ) {
        ScheduleResponseDto editedSchedule = scheduleService.editSchedule(scheduleId, scheduleRequestDto);
        return ResponseEntity.ok(editedSchedule);
    }
}
