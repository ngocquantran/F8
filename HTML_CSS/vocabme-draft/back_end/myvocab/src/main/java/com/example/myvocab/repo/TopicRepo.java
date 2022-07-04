package com.example.myvocab.repo;

import com.example.myvocab.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepo extends JpaRepository<Topic, Integer> {
    List<Topic> findTopicsByCourse_Id(Long id);

    Optional<Topic> findTopicById(Long id);


}