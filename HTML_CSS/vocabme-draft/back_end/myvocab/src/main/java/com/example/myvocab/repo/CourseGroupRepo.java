package com.example.myvocab.repo;

import com.example.myvocab.model.CourseGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseGroupRepo extends JpaRepository<CourseGroup, Integer> {




}