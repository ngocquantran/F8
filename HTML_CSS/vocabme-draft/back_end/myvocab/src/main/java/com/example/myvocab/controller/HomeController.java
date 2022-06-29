package com.example.myvocab.controller;

import com.example.myvocab.model.Course;
import com.example.myvocab.model.CourseGroup;
import com.example.myvocab.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Converter;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class HomeController {
    @Autowired private ViewService viewService;

    @GetMapping(value = "/courses/{category}", produces = "application/json")
    public List<Course> getCoursesByCategory(@PathVariable String category){
        return viewService.getCourseByCategory(category);
    }

    @GetMapping("/courses/group/{category}")
    public List<CourseGroup> getGroupsByCategory(@PathVariable String category){
        return viewService.getGroupsByCategory(category);
    }





}
