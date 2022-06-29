package com.example.myvocab.controller;


import com.example.myvocab.model.Topic;
import com.example.myvocab.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class TopicController {
    @Autowired private ViewService viewService;

    @GetMapping("/vocab-topic/{id}")
    public Topic getTopicById(@PathVariable int id){
        return viewService.getTopicById(id);
    }
}
