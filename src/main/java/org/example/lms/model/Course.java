package org.example.lms.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Setter
    @Column(name = "name")
    private String courseName;

    @Setter
    @Column(name = "description")
    private String courseDescription;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Schedule> schedules = new HashSet<>();

    @Setter
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "courses_groups",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<Group> groups = new HashSet<>(); // act as the owner and define the @JoinTable

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tutor_courses",
            joinColumns = @JoinColumn(name = "course_id", unique = true), // 1 tutor per course
            inverseJoinColumns = @JoinColumn(name = "tutor_id")
    )
    private Tutor tutor;
}
