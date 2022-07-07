package com.example.myvocab.repo;

import com.example.myvocab.dto.UserTopicVocabDto;
import com.example.myvocab.dto.VocabsForChoosing;
import com.example.myvocab.model.UserTopicVocab;
import com.example.myvocab.model.enummodel.LearningStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTopicVocabRepo extends JpaRepository<UserTopicVocab, Long> {
    Optional<UserTopicVocab> findByUserTopic_IdAndVocab_Id(Long id, Long id1);

    Optional<UserTopicVocab> findByUserTopic_IdAndVocab_IdAndLearningStage(Long id, Long id1, LearningStage learningStage);

    List<UserTopicVocab> findByUserTopic_IdAndLearningStage(Long id, LearningStage learningStage);

    boolean existsByUserTopic_IdAndVocab_IdAndLearningStage(Long id, Long id1, LearningStage learningStage);

    @Query("SELECT new com.example.myvocab.dto.VocabsForChoosing(v.id,v.word,v.type,v.img,utv.status) "+
            "FROM UserTopicVocab utv INNER JOIN Vocab v ON utv.vocab=v "+
            " WHERE utv.userTopic.id=:userTopicId AND utv.learningStage=:stage")
    List<VocabsForChoosing> getVocabsForChoosing(Long userTopicId,LearningStage stage);

    @Query("SELECT new com.example.myvocab.dto.UserTopicVocabDto(utv.id,utv.status,utv.learningStage,utv.testTime,utv.userTopic,utv.vocab,utv.learn) "+
            "FROM UserTopicVocab utv "+
            " WHERE utv.userTopic.id=:userTopicId AND utv.learningStage=:stage")
    List<UserTopicVocabDto> getListOfUserTopicVocabDto(Long userTopicId, LearningStage stage);

    long countDistinctByUserTopic_Id(Long id);









}