package com.example.myvocab.service;

import com.example.myvocab.dto.FilterVocabDto;
import com.example.myvocab.dto.TopicToCourseDto;
import com.example.myvocab.exception.NotFoundException;
import com.example.myvocab.model.*;
import com.example.myvocab.repo.*;
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
    @Autowired private UserTopicRepo userTopicRepo;
    @Autowired private VocabRepo vocabRepo;
    @Autowired private SentenceRepo sentenceRepo;
    @Autowired private LevelsRepo levelsRepo;
    @Autowired private CourseGroupRepo courseGroupRepo;
    @Autowired private CourseCategoryRepo categoryRepo;


    public List<Course> getCourseByCategory(Long categoryId){
        return courseRepo.findByCategory_Id(categoryId);
    }

    public List<CourseGroup> getGroupsByCategory(String category){
        return courseRepo.getGroupsByCategory(category);
    }

    public Course getCourseById(Long id){
        Optional<Course> course=courseRepo.findCourseById(id);
        if(!course.isPresent()){
            throw  new NotFoundException("Không có khóa học id = "+id);
        }
        return course.get();
    }



    public List<Topic> getTopicByCourseId(Long id){
       return topicRepo.findTopicsByCourse_Id(id);
    }

    public Topic getTopicById(Long id){
        Optional<Topic> topic=topicRepo.findTopicById(id, Topic.class);
        if(!topic.isPresent()){
            throw new NotFoundException("Không có chủ đề id = "+id);
        }
        return topic.get();
    }

    public List<UserTopic> getUserTopicByCourseIdAndUserId(Long courseId,String userId){
       return userTopicRepo.findByUserCourse_Course_IdAndUserCourse_User_Id(courseId,userId);
    }

    public List<Vocab> getVocabsByTopic(Long topicId){
        return vocabRepo.findByTopics_Id(topicId);
    }

    public List<Sentence> getSentencesByTopic(Long topicId){
        return sentenceRepo.findByTopics_Id(topicId);
    }

    public List<FilterVocabDto> getTopicVocabsToFilter(Long topicId){
        return vocabRepo.getVocabsToFilter(topicId);
    }

    public TopicToCourseDto getCourseFromTopic(Long topicId){
        Optional<TopicToCourseDto> topic=topicRepo.findTopicById(topicId,TopicToCourseDto.class);
        if (topic.isEmpty()){
            throw new NotFoundException("Không tìm thấy topic có id = "+topicId);
        }
        return topic.get();
    }

    public List<Levels> getAllLevels(){
        return levelsRepo.findAll();
    }

    public List<CourseCategory> getAllCategory(){
        return categoryRepo.findAll();
    }

    public List<CourseGroup> getAllGroupByCourseCategory(Long categoryId){
        return courseRepo.getGroupByCourseCategory(categoryId);
    }

}
