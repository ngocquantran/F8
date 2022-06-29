package com.example.myvocab.service;

import com.example.myvocab.exception.NotFoundException;
import com.example.myvocab.model.Course;
import com.example.myvocab.model.CourseGroup;
import com.example.myvocab.model.Topic;
import com.example.myvocab.repo.CourseRepo;
import com.example.myvocab.repo.TopicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
public class ViewService {
    @Autowired private CourseRepo courseRepo;
    @Autowired private EntityManager em;
    @Autowired private TopicRepo topicRepo;

    public List<Course> getCourseByCategory(String category){
        return courseRepo.findCoursesByCategory_TitleEqualsIgnoreCase(category);
    }

    public List<CourseGroup> getGroupsByCategory(String category){
        return courseRepo.getGroupsByCategory(category);
    }

    public Course getCourseById(int id){
        Optional<Course> course=courseRepo.findCourseById(id);
        if(!course.isPresent()){
            throw  new NotFoundException("Không có khóa học id = "+id);
        }
        return course.get();
    }

    public List<Topic> getTopicByCourseId(int id){
       return topicRepo.findTopicsByCourse_Id(id);
    }

    public Topic getTopicById(int id){
        Optional<Topic> topic=topicRepo.findTopicById(id);
        if(!topic.isPresent()){
            throw new NotFoundException("Không có chủ đề id = "+id);
        }
        return topic.get();
    }

}
