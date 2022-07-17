package com.example.myvocab.repo;

import com.example.myvocab.model.Levels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelsRepo extends JpaRepository<Levels, Long> {
}