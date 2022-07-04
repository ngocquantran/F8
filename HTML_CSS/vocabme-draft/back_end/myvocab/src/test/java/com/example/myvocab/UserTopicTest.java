package com.example.myvocab;

import com.example.myvocab.model.*;
import com.example.myvocab.model.enummodel.OrderStatus;
import com.example.myvocab.model.enummodel.TopicState;
import com.example.myvocab.repo.CourseRepo;
import com.example.myvocab.repo.TopicRepo;
import com.example.myvocab.repo.UserTopicRepo;
import com.example.myvocab.repo.UsersRepo;
import com.example.myvocab.service.UserLearningService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserTopicTest {
    @Autowired private UserLearningService userLearningService;
    @Autowired private UserTopicRepo userTopicRepo;
    @Autowired private CourseRepo courseRepo;
    @Autowired private UsersRepo usersRepo;
    @Autowired private TopicRepo topicRepo;
    @Autowired private EntityManager em;

    @Test
    void getUserTopicsByUserIdAndCourseId(){
        List<UserTopic> list=userTopicRepo.findByUserCourse_Course_IdAndUserCourse_User_Id(8L,"1");
        assertThat(list).hasSize(0);
    }

    @Test @Transactional
    void creatUserTopic(){
        Long topicId=9L;
        Topic topic=topicRepo.findTopicById(9L).get();

        Optional<Users> user=usersRepo.findById("1");
        Assertions.assertThat(user).isPresent();

        Optional<Course> course = courseRepo.findByTopicId(topicId);
        assertThat(course).isPresent();

        UserCourse userCourse = UserCourse.builder()
        .user(user.get())
        .course(course.get())
        .build();              //Kiểm tra UserCourse, nếu đã tồn tại không chạy đến phần tạo mới


        Optional<UserTopic> o_userTopic = userTopicRepo.findByTopic_IdAndUserCourse_User_IdAndStatus(topicId, "1", TopicState.PENDING);  //Kiểm tra UserTopic đã tồn tại chưa
        if (o_userTopic.isPresent()) {
            System.out.println("Đã tồn tại");
        }


        UserTopic userTopic = UserTopic.builder()
                .topic(topic)
                .userCourse(userCourse)
                .build();
        userTopic.setStatus(TopicState.PENDING);
        em.persist(userTopic);

    }
}
