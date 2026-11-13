package org.example.lms.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@Entity
@Table(name = "groups")
@Getter
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Setter
    @Column(name = "name", unique = true)
    private String groupName;


    @Setter
    @ManyToMany(mappedBy = "groups")
    private Set<Student> students = new HashSet<>();

    @Setter
    @ManyToMany(mappedBy = "groups") // the recipient
    private Set<Course> courses = new HashSet<>();
}
