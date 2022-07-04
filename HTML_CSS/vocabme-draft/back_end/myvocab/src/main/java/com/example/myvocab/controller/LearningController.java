package com.example.myvocab.controller;

import com.example.myvocab.dto.UserTopicVocabDto;
import com.example.myvocab.dto.VocabsForChoosing;
import com.example.myvocab.model.Course;
import com.example.myvocab.model.UserTopic;
import com.example.myvocab.model.Vocab;
import com.example.myvocab.model.enummodel.LearningStage;
import com.example.myvocab.model.enummodel.TopicState;
import com.example.myvocab.request.FilterVocabRequest;
import com.example.myvocab.service.UserLearningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class LearningController {
    @Autowired private UserLearningService userLearningService;

    @PostMapping(value = "/filter-result/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void postFilterVocabResult(@PathVariable("id") Long topicId, @RequestBody List<FilterVocabRequest> requests){
        // Sau khi lọc từ vựng lần đầu, khởi tạo danh sách user_topic_vocab ở stage First, và user_topic ở status Pending.
        Course course=userLearningService.findCourseByTopicId(topicId);
        userLearningService.createUserCourse(course.getId(),"1"); //Tạm lấy userId =1;
        UserTopic userTopic=userLearningService.createUserTopicWithStatus(topicId,"1", TopicState.PENDING);
        userLearningService.initUserTopicVocabs(topicId,"1", LearningStage.FIRST);
        userLearningService.handleUserTopicVocabsWithStage(topicId,"1",TopicState.PENDING,LearningStage.FIRST,requests);

    }


    @GetMapping("/filter-result/vocabs/{id}")
    public List<UserTopicVocabDto> getVocabListToChooseAfterFilter(@PathVariable("id") Long topicId){
//        Hiện danh sách từ để chọn từ muốn học xong lần filter đầu tiên
//        return userLearningService.getListOfVocabsForChoosing(topicId,"1",LearningStage.FIRST);
        return userLearningService.getListOfUserTopicVocabDto(topicId,"1",LearningStage.FIRST);

    }


    @PostMapping(value = "/filter-result/vocabs/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void postListOfChosedWordToLearn(@PathVariable("id") Long topicId,@RequestBody List<UserTopicVocabDto> requests){
//        Lấy danh sách từ đã chọn để học sau khi lọc lần đầu và lưu vào database giai đoạn FIRST

        userLearningService.updateUserTopicVocabAfterChooseWordToLearn(topicId,"1",LearningStage.FIRST, requests);

    }

    @GetMapping("/learning/vocabs/{id}")
    public List<Vocab> getListOfVocabsLearning(@PathVariable("id") Long topicId){
        return userLearningService.getListOfVocabByTopicToLearn(topicId,"1");
    }


}
