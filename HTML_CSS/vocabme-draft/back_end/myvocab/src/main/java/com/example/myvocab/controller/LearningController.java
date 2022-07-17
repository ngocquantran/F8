package com.example.myvocab.controller;

import com.example.myvocab.dto.ChooseVocabDto;
import com.example.myvocab.dto.ContextDto;
import com.example.myvocab.model.Sentence;
import com.example.myvocab.model.Vocab;
import com.example.myvocab.request.FilterVocabRequest;
import com.example.myvocab.request.LearnVocabRequest;
import com.example.myvocab.service.UserLearningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class LearningController {
    @Autowired
    private UserLearningService userLearningService;

    @PostMapping(value = "topic/{id}/filter-result", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void submitFilterVocabResult(@PathVariable("id") Long topicId, @RequestBody List<FilterVocabRequest> requests) {
        System.out.println(requests);
        userLearningService.handleSubmittedFilterVocabResult(topicId, "1", requests);
    }


    @GetMapping("topic/{id}/filter-result/vocabs-to-choose")
    public List<ChooseVocabDto> getVocabTopicToChooseToLearn(@PathVariable("id") Long topicId) {
        return userLearningService.getTopicVocabToChoose(topicId, "1");
    }

    @PostMapping(value = "topic/{id}/filter-result/vocabs-to-learn", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void postListOfChosedWordToLearn(@PathVariable("id") Long topicId, @RequestBody List<LearnVocabRequest> requests) {
//        Lấy danh sách từ đã chọn để học  và lưu vào database
        userLearningService.saveLearnRequestToUserTopicVocab(topicId, "1", requests);
    }

    @GetMapping("topic/{id}/learning/vocabs")
    public List<Vocab> getListOfVocabsLearning(@PathVariable("id") Long topicId) {
        return userLearningService.getTopicVocabsToLearn(topicId, "1");
    }

    @GetMapping("topic/{id}/learning/sentence")
    public List<Sentence> getListOfSentenceLearning(@PathVariable("id") Long topicId){
        return userLearningService.getTopicSentenceToLearn(topicId);
    }

    @GetMapping("topic/learning/sentence/{id}/context")
    public List<ContextDto> getContextBySentence(@PathVariable("id") Long sentenceId){
        return userLearningService.getContextsBySentence(sentenceId);    }






}
