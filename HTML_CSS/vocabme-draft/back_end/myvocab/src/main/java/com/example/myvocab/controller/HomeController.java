package com.example.myvocab.controller;

import com.example.myvocab.model.Course;
import com.example.myvocab.model.CourseCategory;
import com.example.myvocab.model.CourseGroup;
import com.example.myvocab.model.Levels;
import com.example.myvocab.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Converter;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class HomeController {
    @Autowired private ViewService viewService;

    @GetMapping(value = "/courses/{categoryId}", produces = "application/json")
    public List<Course> getCoursesByCategory(@PathVariable Long categoryId){
        return viewService.getCourseByCategory(categoryId);
    }

    @GetMapping("/courses/group/{categoryId}")
    public List<CourseGroup> getGroupsByCategory(@PathVariable Long categoryId){
        return viewService.getAllGroupByCourseCategory(categoryId);
    }

    @GetMapping("courses/category")
    public List<CourseCategory> getAllCategory(){
        return viewService.getAllCategory();
    }

    @GetMapping("courses/levels")
    public List<Levels> getAllLevels(){
        return viewService.getAllLevels();
    }







}
