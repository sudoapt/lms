package org.example.lms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.lms.dto.ScheduleFilterDto;
import org.example.lms.dto.ScheduleRequestDto;
import org.example.lms.dto.ScheduleResponseDto;
import org.example.lms.model.Schedule;
import org.example.lms.service.ScheduleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/schedules")
    @Operation(summary = "Filter schedule")
    public PagedModel<ScheduleResponseDto> getScheduleByFilter(
            @RequestBody(required = false) ScheduleFilterDto scheduleFilterDto,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<ScheduleResponseDto> schedulePage = scheduleService.filterSchedules(scheduleFilterDto, pageable);
        return new PagedModel<>(schedulePage);
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

    @DeleteMapping("/{scheduleId}")
    @Operation(summary = "Delete a schedule")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long scheduleId
    ) {
        scheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
