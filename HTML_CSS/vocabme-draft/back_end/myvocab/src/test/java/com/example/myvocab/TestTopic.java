package com.example.myvocab;

import com.example.myvocab.model.Course;
import com.example.myvocab.model.Topic;
import com.example.myvocab.repo.TopicRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.*;

import javax.persistence.EntityManager;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestTopic {
    @Autowired private TopicRepo topicRepo;
    @Autowired private EntityManager em;

    @Test
    void findTopicByCourseId(){
        List<Topic> topics=topicRepo.findTopicsByCourse_Id(15L);
        assertThat(topics).extracting(Topic::getCourse).extracting(Course::getId).containsOnly(15L);
    }
}
