package org.example.lms.service;

import lombok.RequiredArgsConstructor;
import org.example.lms.repository.ScheduleRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ScheduleCleanupService {

    private final ScheduleRepository scheduleRepository;

    @Scheduled(cron = "${app.scheduling.cleanup-cron}")
    @Transactional
    public void deleteExpiredSchedule() {
        LocalDateTime cleanupCondition = LocalDateTime.now().minusDays(2);

        int schedulesCleaned = scheduleRepository.killScheduleOlderThan(cleanupCondition);
        System.out.println("Schedules cleaned: " + schedulesCleaned);

    }
}
