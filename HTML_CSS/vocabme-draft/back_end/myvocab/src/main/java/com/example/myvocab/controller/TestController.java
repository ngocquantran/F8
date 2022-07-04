package com.example.myvocab.controller;

import com.example.myvocab.dto.UserTopicVocabDto;
import com.example.myvocab.dto.VocabTest;
import com.example.myvocab.model.Sentence;
import com.example.myvocab.model.Vocab;
import com.example.myvocab.service.UserLearningService;
import com.example.myvocab.service.UserTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {
    @Autowired private UserLearningService userLearningService;
    @Autowired private UserTestService userTestService;

    @GetMapping("/{id}/vocabs")
    public List<VocabTest> getListOfVocabsForTest(@PathVariable("id") Long topicId){
        return userTestService.getTestVocabs(topicId);
    }

    @GetMapping("{id}/sentences")
    public List<Sentence> getListOfSentencesForTest(@PathVariable("id") Long topicId){
        return userTestService.getListOfSentenceForTest(topicId);
    }

    @PostMapping( value = "{id}/vocabs/test-result",consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<UserTopicVocabDto> submitVocabTestResult(@PathVariable("id") Long topicId,@RequestBody List<UserTopicVocabDto> requests){

        return null;
    }

}
