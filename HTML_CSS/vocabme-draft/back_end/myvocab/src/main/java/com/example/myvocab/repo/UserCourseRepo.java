package com.example.myvocab.repo;

import com.example.myvocab.model.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCourseRepo extends JpaRepository<UserCourse, Long> {
    Optional<UserCourse> findByCourse_IdAndUser_Id(Long id, String id1);
}