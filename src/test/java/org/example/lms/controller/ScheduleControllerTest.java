package org.example.lms.controller;

import org.example.lms.dto.ScheduleFilterDto;
import org.example.lms.dto.ScheduleResponseDto;
import org.example.lms.model.Course;
import org.example.lms.model.Group;
import org.example.lms.model.Schedule;
import org.example.lms.model.Tutor;
import org.example.lms.repository.CourseRepository;
import org.example.lms.repository.GroupRepository;
import org.example.lms.repository.ScheduleRepository;
import org.example.lms.repository.TutorRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
@Testcontainers
class ScheduleControllerTest {

    @Container
    static PostgreSQLContainer postgreSQLContainer =
            new PostgreSQLContainer("postgres:latest");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");

    }


    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    TutorRepository tutorRepository;

    @BeforeEach
    void setUp() {
        scheduleRepository.deleteAll();
        tutorRepository.deleteAll();
        courseRepository.deleteAll();
        groupRepository.deleteAll();

        Tutor tutor1 = new Tutor();
        tutor1.setFirstName("Hugo");
        tutor1.setLastName("Niels");
        tutorRepository.save(tutor1);

        Course course1 = new Course();
        course1.setCourseName("DTS");
        courseRepository.save(course1);
        Set<Course> courses = new HashSet<>();
        courses.add(course1);


        Group group1 = new Group();
        group1.setGroupName("GroupA");
        group1.setCourses(courses);
        groupRepository.save(group1);


        Schedule s1 = new Schedule();
        s1.setStartDate(LocalDate.of(2026, 11, 15));
        s1.setEndDate(LocalDate.of(2026, 12, 15));
        s1.setTutor(tutor1);
        s1.setGroup(group1);
        s1.setCourse(course1);

        // Matches the filter (within Nov 14 - Nov 24, 2026)
        Schedule s2 = new Schedule();
        s2.setStartDate(LocalDate.of(2026, 11, 18));
        s2.setEndDate(LocalDate.of(2026, 12, 18));
        s2.setTutor(tutor1);
        s2.setGroup(group1);
        s2.setCourse(course1);

        // Does NOT match the filter (outside date range & different group)
        Schedule s3 = new Schedule();
        s3.setStartDate(LocalDate.of(2026, 10, 1));
        s3.setEndDate(LocalDate.of(2026, 12, 11));
        s3.setTutor(tutor1);
        s3.setGroup(group1);
        s3.setCourse(course1);

        scheduleRepository.saveAll(List.of(s1, s2, s3));

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createSchedule() {
    }

    @Test
    void shouldFilterScheduleWithPagination() {
        ScheduleFilterDto filterDto = new ScheduleFilterDto(
                null,
                null,
                null,
                null,
                null,
                LocalDate.of(2026, 11, 14),
                LocalDate.now().plusDays(7));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ScheduleFilterDto> requestEntity = new HttpEntity<>(filterDto, httpHeaders); // bind headers and body

        ResponseEntity<PagedResponse<ScheduleResponseDto>> response = restTemplate
                .exchange("/schedules?page=0&size=2",
                        HttpMethod.POST,
                        requestEntity,
                        new ParameterizedTypeReference<>() {
                        });
        System.out.println("RAW STATUS: " + response.getStatusCode());
        System.out.println("RAW BODY: " + response.getBody());


        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().content()).hasSize(2);
        assertThat(response.getBody().page().totalElements()).isGreaterThanOrEqualTo(2);
        assertThat(response.getBody().page().size()).isEqualTo(2);
        assertThat(response.getBody().page().number()).isZero();
    }

    @Test
    void editSchedule() {
    }

    @Test
    void deleteSchedule() {
    }

    /**
     * helper records to match the JSON structure serialized by Spring Data's PagedModel.
     */
    record PagedResponse<T>(List<T> content, PageMetadata page) {}

    record PageMetadata(int size, int number, long totalElements, int totalPages) {}
}