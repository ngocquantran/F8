package com.example.myvocab.repo;

import com.example.myvocab.model.Course;
import com.example.myvocab.model.CourseCategory;
import com.example.myvocab.model.CourseGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepo extends JpaRepository<Course,Integer> {

    @Query("select c from Course c where upper(c.category.title) = upper(?1)")
    List<Course> findCoursesByCategory_TitleEqualsIgnoreCase(String category);

    List<Course> findCoursesByCategory(CourseCategory category);

    List<Course> findCoursesByGroup(CourseGroup group);

    Optional<Course> findCourseById(int id);


@Query("select c.group from Course c where upper(c.category.title) = upper(?1) group by c.group")
    List<CourseGroup> getGroupsByCategory(String category);


}