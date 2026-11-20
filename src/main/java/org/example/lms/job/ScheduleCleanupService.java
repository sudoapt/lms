package org.example.lms.job;

import lombok.RequiredArgsConstructor;
import org.example.lms.repository.ScheduleRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ScheduleCleanupService {

    private final ScheduleRepository scheduleRepository;

    @Scheduled(cron = "${app.scheduling.cleanup-cron}")
    @Transactional
    public void deleteExpiredSchedule() {
        LocalDate cleanupCondition = LocalDate.now().minusDays(2);

        int schedulesCleaned = scheduleRepository.deleteByEndDateBefore(cleanupCondition);
        System.out.println("Schedules cleaned: " + schedulesCleaned);

    }
}
