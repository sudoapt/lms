package org.example.lms.service;

import lombok.RequiredArgsConstructor;
import org.example.lms.dto.ScheduleDto;
import org.example.lms.mapper.*;
import org.example.lms.model.Schedule;
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
    private final StudentRepository studentRepository;

    private final ScheduleMapper scheduleMapper;

    @Transactional
    public ScheduleDto createSchedule(ScheduleDto scheduleDto) {
     Schedule scheduleToEntity = scheduleMapper.toScheduleEntity(scheduleDto);
     Schedule newSchedule = scheduleRepository.save(scheduleToEntity);
     return scheduleMapper.toDto(newSchedule);
    }

}
