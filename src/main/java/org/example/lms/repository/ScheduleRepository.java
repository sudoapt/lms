package org.example.lms.repository;

import org.example.lms.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long>, JpaSpecificationExecutor<Schedule> {
    @Modifying
    @Query("DELETE from Schedule s where s.endDate < :expirationDate")
    int killScheduleOlderThan(@Param("expirationDate")LocalDateTime expirationDate);
}
