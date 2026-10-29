package org.example.lms.model;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY) // many schedules can have the same tutor
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY) // many schedules for group
    @JoinColumn(name = "group_id")
    private Group group;

    @Setter
    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Setter
    @Column(name = "end_date")
    private LocalDateTime endDate;


}
