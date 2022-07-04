package com.example.myvocab.repo;

import com.example.myvocab.model.Vocab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VocabRepo extends JpaRepository<Vocab, Long> {
    List<Vocab> findByTopics_Id(Long id);

    List<Vocab> findByTopics_Course_Id(Long id);

}