package org.example.lms.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"tutor", "course", "group"})
@EqualsAndHashCode(exclude = {"tutor", "course", "group"}) // exclude from fetching
@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // many schedules can have the same tutor
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY) // many schedules for group
    @JoinColumn(name = "group_id")
    private Group group;


    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;


}
