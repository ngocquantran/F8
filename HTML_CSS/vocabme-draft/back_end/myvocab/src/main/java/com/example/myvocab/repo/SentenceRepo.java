package com.example.myvocab.repo;

import com.example.myvocab.model.Sentence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SentenceRepo extends JpaRepository<Sentence, Long> {
    List<Sentence> findByTopics_Id(Long id);

}