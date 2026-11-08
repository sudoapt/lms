package org.example.lms.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.lms.dto.ScheduleRequestDto;
import org.example.lms.dto.ScheduleResponseDto;
import org.example.lms.mapper.*;
import org.example.lms.model.Course;
import org.example.lms.model.Group;
import org.example.lms.model.Schedule;
import org.example.lms.model.Tutor;
import org.example.lms.repository.*;
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
            throw new IllegalArgumentException("Start date cannot be after or equal to the end date.");
        }
    }

    @Transactional
    public ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto) {
        Schedule scheduleToEntity = scheduleMapper.toScheduleEntity(scheduleRequestDto);

        Tutor tutor = tutorRepository.getOrThrow(scheduleRequestDto.tutorId());
        Course course = courseRepository.getOrThrow(scheduleRequestDto.courseId());
        Group group = groupRepository.getOrThrow(scheduleRequestDto.groupId());
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
        Schedule schedule = scheduleRepository.getOrThrow(scheduleId);

        validateDates(scheduleRequestDto);





    }

}
