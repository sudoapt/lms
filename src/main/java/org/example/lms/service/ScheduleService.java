package org.example.lms.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.lms.dto.ScheduleFilterDto;
import org.example.lms.dto.ScheduleRequestDto;
import org.example.lms.dto.ScheduleResponseDto;
import org.example.lms.mapper.*;
import org.example.lms.model.Course;
import org.example.lms.model.Group;
import org.example.lms.model.Schedule;
import org.example.lms.model.Tutor;
import org.example.lms.repository.*;
import org.example.lms.specification.ScheduleSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final TutorRepository tutorRepository;
    private final CourseRepository courseRepository;
    private final GroupRepository groupRepository;

    private final ScheduleMapper scheduleMapper;

    private void validateDates(ScheduleRequestDto dto) {
        if (dto.endDate().isBefore(dto.startDate()) || dto.endDate().isEqual(dto.startDate())) {
            throw new IllegalArgumentException("Start date cannot be after or equal the end date.");
        }
    }

    private void validateUpdatedDates(Schedule schedule) {
        if (schedule.getEndDate() != null && schedule.getStartDate() != null) {
            if (schedule.getEndDate().isBefore(schedule.getStartDate()) || schedule.getEndDate().isEqual(schedule.getStartDate())) {
                throw new IllegalArgumentException("Start date cannot be after or equal the end date.");
            }
        }
    }


    private Tutor fetchTutor(Long tutorId) {
        return tutorRepository.findById(tutorId)
                .orElseThrow(() -> new EntityNotFoundException("No tutor with the id: " + tutorId + " is found"));
    }

    private Course fetchCourse(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("No tutor with the id: " + courseId + " is found"));
    }

    private Group fetchGroup(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("No tutor with the id: " + groupId + " is found"));
    }

    @Transactional
    public ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto) {
        Schedule scheduleToEntity = scheduleMapper.toScheduleEntity(scheduleRequestDto);

        Tutor tutor = fetchTutor(scheduleRequestDto.tutorId());
        Course course = fetchCourse(scheduleRequestDto.courseId());
        Group group = fetchGroup(scheduleRequestDto.groupId());
        validateDates(scheduleRequestDto);

        scheduleToEntity.setTutor(tutor);
        scheduleToEntity.setGroup(group);
        scheduleToEntity.setCourse(course);

        scheduleToEntity.setStartDate(scheduleRequestDto.startDate());
        scheduleToEntity.setEndDate(scheduleRequestDto.endDate());

        Schedule newSchedule = scheduleRepository.save(scheduleToEntity);
        return scheduleMapper.toDto(newSchedule);
    }


    @Transactional
    public ScheduleResponseDto editSchedule(Long scheduleId, ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new EntityNotFoundException("No schedule with the id " + scheduleId + " is found."));


        if (scheduleRequestDto.tutorId() != null) {
            schedule.setTutor(fetchTutor(scheduleRequestDto.tutorId()));
        }
        if (scheduleRequestDto.groupId() != null) {
            schedule.setGroup(fetchGroup(scheduleRequestDto.groupId()));
        }
        if (scheduleRequestDto.courseId() != null) {
            schedule.setCourse(fetchCourse(scheduleRequestDto.courseId()));
        }
        if (scheduleRequestDto.startDate() != null) {
            schedule.setStartDate(scheduleRequestDto.startDate());
        }
        if (scheduleRequestDto.endDate() != null) {
            schedule.setEndDate(scheduleRequestDto.endDate());
        }

        validateUpdatedDates(schedule);

        return scheduleMapper.toDto(schedule);

    }

    @Transactional
    public Page<ScheduleResponseDto> getSchedules(Pageable pageable) {
        Page<Schedule> schedulesPage = scheduleRepository.findAll(pageable);
        return schedulesPage.map(scheduleMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ScheduleResponseDto> filterSchedules(ScheduleFilterDto scheduleFilterDto, Pageable pageable) {
        Page<Schedule> schedulePage;
        // check if no data for filter is passed
        if (scheduleFilterDto == null || scheduleFilterDto.isEmpty()) {
            schedulePage = scheduleRepository.findAll(pageable);
        } else {
            schedulePage = scheduleRepository.findAll(ScheduleSpecification.scheduleByFilter(scheduleFilterDto), pageable);
        }
        return schedulePage.map(scheduleMapper::toDto);
    }

    @Transactional
    public void deleteSchedule(Long scheduleId) {
        if (!scheduleRepository.existsById(scheduleId)) {
            throw new EntityNotFoundException("No student with the id: " + scheduleId + " found");
        }
        scheduleRepository.deleteById(scheduleId);
    }
}
