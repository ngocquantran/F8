package com.example.myvocab.controller;

import com.example.myvocab.dto.UserTopicRankDto;
import com.example.myvocab.dto.VocabTestDto;
import com.example.myvocab.dto.VocabTestResultDto;
import com.example.myvocab.model.Sentence;
import com.example.myvocab.model.UserTopicRecord;
import com.example.myvocab.model.enummodel.TopicState;
import com.example.myvocab.request.TestVocabResultRequest;
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
    public List<VocabTestDto> getListOfVocabsForTest(@PathVariable("id") Long topicId){
        return userLearningService.getTestVocabs(topicId);
    }

    @GetMapping("{id}/sentences")
    public List<Sentence> getListOfSentencesForTest(@PathVariable("id") Long topicId){
        return userLearningService.getListOfSentenceForTest(topicId);
    }

    @PostMapping( value = "{id}/vocabs/test-result",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void submitVocabTestResult(@PathVariable("id") Long topicId,@RequestBody List<TestVocabResultRequest> requests){
        userLearningService.handleVocabTestResult(topicId,"1",requests);
    }

    @GetMapping("{id}/vocabs/test-result")
    public List<VocabTestResultDto> getTestResultStageNow(@PathVariable("id") Long topicId){
        return userLearningService.getVocabsTestResult(topicId,"1");
    }

    @GetMapping("{id}/vocabs/test-result/record")
    public List<UserTopicRecord> getUserTopicRecord(@PathVariable("id") Long topicId){
        return userLearningService.getTopicRecords(topicId,"1");
    }

    @GetMapping("{id}/vocabs/test-result/top-rank")
    public List<UserTopicRankDto> getListUserTopicTopRank(@PathVariable("id") Long topicId){
        return userLearningService.getTopTenRank(topicId);
    }

    @GetMapping("{id}/vocabs/test-result/user-rank")
    public UserTopicRankDto getUserTopicRank(@PathVariable("id") Long topicId){
        return userLearningService.getUserTopicRank(topicId,"1");
    }


    @GetMapping("{id}/vocabs/test-result/topic-state")
    public TopicState getUserTopicStateAndCourse(@PathVariable("id") Long topicId){
        return userLearningService.getUserTopicState(topicId,"1");
    }

}
