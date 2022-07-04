package com.example.myvocab.controller;

import com.example.myvocab.model.Course;
import com.example.myvocab.model.Topic;
import com.example.myvocab.model.UserTopic;
import com.example.myvocab.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/course/")
public class CourseController {
    @Autowired private ViewService viewService;

    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable Long id){
        return viewService.getCourseById(id);
    }

    @GetMapping("/{id}/topics")
    public List<Topic> getTopicsByCourseId(@PathVariable Long id){
        return viewService.getTopicByCourseId(id);
    }

    @GetMapping("/user-topic")
    public List<UserTopic> getUserTopicsByCourseAndUser(@RequestParam Long courseId,@RequestParam String userId){
        return viewService.getUserTopicByCourseIdAndUserId(courseId,userId);
    }






}
