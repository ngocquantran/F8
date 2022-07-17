package com.example.myvocab.controller;


import com.example.myvocab.dto.FilterVocabDto;
import com.example.myvocab.dto.TopicToCourseDto;
import com.example.myvocab.model.Sentence;
import com.example.myvocab.model.Topic;
import com.example.myvocab.model.Vocab;
import com.example.myvocab.service.UserLearningService;
import com.example.myvocab.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TopicController {
    @Autowired private ViewService viewService;
    @Autowired private UserLearningService userLearningService;

    @GetMapping("/topic/{id}")
    public Topic getTopicById(@PathVariable Long id){
        return viewService.getTopicById(id);
    }

    @GetMapping("/topic/{id}/vocabs")
    public List<Vocab> getVocabsByTopic(@PathVariable("id") Long topicId){
        return viewService.getVocabsByTopic(topicId);
    }

    @GetMapping("/topic/{id}/sentences")
    public List<Sentence> getSentencesByTopic(@PathVariable("id") Long topicId){
        return viewService.getSentencesByTopic(topicId);
    }

    @GetMapping("/topic/{id}/filter-vocabs")
    public List<FilterVocabDto> getTopicVocabsToFilter(@PathVariable("id") Long topicId){
        return viewService.getTopicVocabsToFilter(topicId);
    }

    @GetMapping("/topic/{id}/to-course")
    public TopicToCourseDto getCourseFromTopic(@PathVariable("id") Long topicId){
        return viewService.getCourseFromTopic(topicId);
    }


}
