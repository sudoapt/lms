package org.example.lms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.lms.dto.ScheduleRequestDto;
import org.example.lms.dto.ScheduleResponseDto;
import org.example.lms.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/schedule")
@Tag(name = "Schedules", description = "Schedule management")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PutMapping
    @Operation(summary = "Create a schedule")
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto) {
        ScheduleResponseDto createSchedule = scheduleService.createSchedule(scheduleRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createSchedule);
    }

}
