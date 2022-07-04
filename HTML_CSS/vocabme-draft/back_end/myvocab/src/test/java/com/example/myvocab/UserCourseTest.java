package com.example.myvocab;


import com.example.myvocab.model.Course;
import com.example.myvocab.model.UserCourse;
import com.example.myvocab.model.Users;
import com.example.myvocab.repo.CourseRepo;
import com.example.myvocab.repo.UserCourseRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserCourseTest {
    @Autowired private UserCourseRepo userCourseRepo;
    @Autowired private CourseRepo courseRepo;
    @Autowired private EntityManager em;

    @Test @Transactional
    void save_user_course() {

        Optional<Course> course=courseRepo.findCourseById(1L);
        assertThat(course).isPresent();

        Users user = Users.builder()
                .userName("Quan")
                .email("quan@gmail.com")
                .password("12345678")
                .birth(LocalDate.of(1994, 07, 31))
                .build();
        assertThat(user.getId()).isNull();
        em.persist(user);
        em.flush();

        Optional<UserCourse> userCourse1 = userCourseRepo.findByCourse_IdAndUser_Id(1L, user.getId());
        assertThat(userCourse1).isNotPresent();

        UserCourse userCourse2=UserCourse.builder()
                .course(course.get())
                .user(user)
                .build();
        em.persist(userCourse2);

        Optional<UserCourse> userCourse3 = userCourseRepo.findByCourse_IdAndUser_Id(1L, user.getId());
        assertThat(userCourse3).isPresent();








    }
}
