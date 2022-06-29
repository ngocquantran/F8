package com.example.myvocab;

import com.example.myvocab.model.Course;
import com.example.myvocab.model.CourseCategory;
import com.example.myvocab.model.CourseGroup;
import com.example.myvocab.repo.CourseGroupRepo;
import com.example.myvocab.repo.CourseRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestCourse {
    @Autowired
    private CourseRepo courseRepo;

    @Autowired private CourseGroupRepo courseGroupRepo;

    @Autowired private EntityManager em;

    @Test
    void createCourse(){
        CourseGroup group1=new CourseGroup("Nhóm 1");
        CourseGroup group2=new CourseGroup("Nhóm 2");
        CourseCategory cat1=new CourseCategory("Từ vựng");
        CourseCategory cat2=new CourseCategory("Câu");


        Course course1=Course.builder().title("Khóa thứ nhất")
                .description("Diễn giải 1")
                .content("Nội dung 1")
                .targetLearner("Đối tượng 1")
                .goal("Mục tiêu 1")
                .thumbnail("ảnh 1")
                .group(group1)
                .category(cat1)
                .build();
        Course course2=Course.builder().title("Khóa thứ hai")
                .description("Diễn giải 2")
                .content("Nội dung 2")
                .targetLearner("Đối tượng 2")
                .goal("Mục tiêu2")
                .thumbnail("ảnh 2")
                .group(group2)
                .category(cat2)
                .build();
        em.persist(course1);
        em.persist(course2);
        em.flush();
        long count=courseRepo.count();
        assertThat(count).isEqualTo(2L);
    }

    @Test
    void getCourseByCategory(){
        CourseGroup group1=new CourseGroup("Nhóm 1");
        CourseGroup group2=new CourseGroup("Nhóm 2");
        CourseCategory cat1=new CourseCategory("Từ vựng");
        CourseCategory cat2=new CourseCategory("Câu");

        Course course1=Course.builder().title("Khóa thứ nhất")
                .description("Diễn giải 1")
                .content("Nội dung 1")
                .targetLearner("Đối tượng 1")
                .goal("Mục tiêu 1")
                .thumbnail("ảnh 1")
                .group(group1)
                .category(cat1)
                .build();
        Course course2=Course.builder().title("Khóa thứ hai")
                .description("Diễn giải 2")
                .content("Nội dung 2")
                .targetLearner("Đối tượng 2")
                .goal("Mục tiêu2")
                .thumbnail("ảnh 2")
                .group(group2)
                .category(cat2)
                .build();
        em.persist(course1);
        em.persist(course2);

        List<Course> courses=courseRepo.findCoursesByCategory(cat1);
        assertThat(courses).extracting(Course::getCategory).containsOnly(cat1);
        assertThat(courses.size()).isEqualTo(1);
        List<Course> courses1=courseRepo.findCoursesByGroup(group2);
        assertThat(courses1).extracting(Course::getGroup).containsOnly(group2);
        assertThat(courses1.size()).isEqualTo(1);

    }

    @Test @Transactional
    void getTopicsOfCourse(){
        Optional<Course> course=courseRepo.findCourseById(1);
        assertThat(course).isPresent();
        assertThat(course.get().getNumberOfTopics()).isEqualTo(7);
        assertThat(course.get().getCategory().getTitle()).isEqualTo("Từ vựng");

    }

    @Test
    void getCourseByCategoryString(){
        List<Course> courses=courseRepo.findCoursesByCategory_TitleEqualsIgnoreCase("Từ vựng");
        assertThat(courses).extracting(Course::getCategory).extracting(CourseCategory::getTitle).containsOnly("Từ vựng");
    }

    @Test
    void getAllGroup(){
        List<CourseGroup> groups=courseGroupRepo.findAll();
        assertThat(groups).hasSize(12);
    }

    @Test
    void getGroupByCategory(){
        List<CourseGroup> groups=courseRepo.getGroupsByCategory("Từ vỰng");
        assertThat(groups).hasSize(8);
    }


}
