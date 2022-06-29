package com.example.myvocab.controller;

import com.example.myvocab.model.Course;
import com.example.myvocab.model.Topic;
import com.example.myvocab.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CourseController {
    @Autowired private ViewService viewService;

    @GetMapping("course/{id}")
    public Course getCourseById(@PathVariable int id){
        return viewService.getCourseById(id);
    }

    @GetMapping("/course/{id}/topics")
    public List<Topic> getTopicsByCourseId(@PathVariable int id){
        return viewService.getTopicByCourseId(id);
    }


}
