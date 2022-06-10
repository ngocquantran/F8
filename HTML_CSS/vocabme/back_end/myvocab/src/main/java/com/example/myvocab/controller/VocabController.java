package com.example.myvocab.controller;


import com.example.myvocab.model.Vocab;
import com.example.myvocab.repo.VocabRepo;
import com.example.myvocab.service.VocabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class VocabController {
    @Autowired private VocabRepo vocabRepo;
    @Autowired private VocabService vocabService;

    @GetMapping("/filter-data/{topicId}")
    public List<Vocab> getTopicVocabs(@PathVariable int topicId){
        return vocabService.getTopicVocabs(topicId);
    }

    @GetMapping("/learning/{topicId}")
    public List<Vocab> getStudyVocabs(@PathVariable int topicId){
        return vocabService.getStudyVocabs(topicId);
    }

    @GetMapping("/test/{topicId}")
    public List<Vocab> getTestVocabs(@PathVariable int topicId){
        return vocabService.getTestVocabs(topicId);
    }
}
