package com.example.myvocab.controller;

import com.example.myvocab.dto.UserTopicVocabDto;
import com.example.myvocab.dto.VocabTest;
import com.example.myvocab.model.Sentence;
import com.example.myvocab.model.UserTopicVocab;
import com.example.myvocab.model.enummodel.LearningStage;
import com.example.myvocab.service.UserLearningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {
    @Autowired private UserLearningService userLearningService;


    @GetMapping("/{id}/vocabs")
    public List<VocabTest> getListOfVocabsForTest(@PathVariable("id") Long topicId){
        return userLearningService.getTestVocabs(topicId);
    }

    @GetMapping("{id}/sentences")
    public List<Sentence> getListOfSentencesForTest(@PathVariable("id") Long topicId){
        return userLearningService.getListOfSentenceForTest(topicId);
    }

    @PostMapping( value = "{id}/vocabs/test-result",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void submitVocabTestResult(@PathVariable("id") Long topicId,@RequestBody List<UserTopicVocabDto> requests){

        userLearningService.handleVocabTestResult(topicId,"1",requests);

    }

    @GetMapping("{id}/vocabs/test-result/now")
    public List<UserTopicVocab> getTestResultStageNow(@PathVariable("id") Long topicId){
        return userLearningService.getTestResultNowStage(topicId,"1");
    }

    @GetMapping("{id}/vocabs/test-result/chart")
    public List<String> getTopChartTestResult(@PathVariable("id") Long topicId){
        return null;
    }

}
