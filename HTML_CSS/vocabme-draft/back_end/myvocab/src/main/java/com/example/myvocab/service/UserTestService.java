package com.example.myvocab.service;


import com.example.myvocab.exception.BadRequestException;
import com.example.myvocab.mapper.UserTopicVocabMapper;
import com.example.myvocab.mapper.VocabTestMapper;
import com.example.myvocab.model.Course;
import com.example.myvocab.model.Sentence;
import com.example.myvocab.model.Topic;
import com.example.myvocab.model.Vocab;
import com.example.myvocab.repo.*;
import com.example.myvocab.dto.VocabTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserTestService {
    @Autowired
    UserTopicVocabMapper mapper;
    @Autowired
    private UserCourseRepo userCourseRepo;
    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private CourseRepo courseRepo;
    @Autowired
    private TopicRepo topicRepo;
    @Autowired
    private UserTopicRepo userTopicRepo;
    @Autowired
    private VocabRepo vocabRepo;
    @Autowired
    private UserTopicVocabRepo userTopicVocabRepo;



    //    Lấy danh sách từ vựng trong topic để render bài test

    public List<Sentence> getListOfSentenceForTest(Long topicId){
        Topic topic=isTopicExist(topicId);
        return topic.getSentences().stream().toList();
    }


    public List<VocabTest> getTestVocabs(Long topicId){
        Topic topic = isTopicExist(topicId);

        List<VocabTest> words=topic.getVocabs().stream().toList()
                .stream()
                .map(vocab -> VocabTestMapper.toVocabTest(vocab))
                .map(vocabTest -> renderVocabAnswers(vocabTest,topic.getCourse().getId()))
                .map(vocabTest -> renderVnMeaningAnswers(vocabTest,topic.getCourse().getId()))
                .map(vocabTest -> renderEnSentenceAnswers(vocabTest,topic.getCourse().getId()))
                .collect(Collectors.toList());

        return words;
    }


//    Render answer list for VocabTest (Choosing 4 options)

    public VocabTest renderVocabAnswers(VocabTest vocabTest, Long courseId) {
        int answerIndex = vocabTest.getAnswerIndex();
        List<String> vocabs = getCourseVocabs(courseId);

        List<String> wordLists = new ArrayList<>();
        int index = 0;
        boolean isContinue = true;
        Random rd = new Random();
        while (isContinue) {
            if (index + 1 == answerIndex) {
                wordLists.add(vocabTest.getWord());
                index++;
            } else {
                int i = rd.nextInt(vocabs.size());
                if (!vocabs.get(i).equals(vocabTest.getWord()) & !wordLists.contains(vocabs.get(i))) {
                    wordLists.add(vocabs.get(i));
                    index++;
                }
            }
            if (index >= 4) {
                isContinue = false;
            }
        }
        vocabTest.setVocabs(wordLists);
        return vocabTest;
    }


    public VocabTest renderVnMeaningAnswers(VocabTest vocabTest,Long courseId) {
        int answerIndex = vocabTest.getAnswerIndex();
        List<String> vnMeanings=getCourseVnMeaning(courseId);
        List<String> vnLists=new ArrayList<>();
        int index=0;
        boolean isContinue=true;
        Random rd=new Random();
        while (isContinue){
            if (index+1==answerIndex){
                vnLists.add(vocabTest.getVnMeaning());
                index++;
            }else {
                int i=rd.nextInt(vnMeanings.size());
                if (!vnMeanings.get(i).equals(vocabTest.getVnMeaning()) & !vnLists.contains(vnMeanings.get(i))) {
                    vnLists.add(vnMeanings.get(i));
                    index++;
                }
            }
            if (index>=4){isContinue=false;}
        }

        vocabTest.setVnMeanings(vnLists);
        return vocabTest;
    }

    public VocabTest renderEnSentenceAnswers(VocabTest vocabTest,Long courseId) {
        int answerIndex = vocabTest.getAnswerIndex();
        List<String> enSentences=getCourseEnSentence(courseId);
        List<String> enLists=new ArrayList<>();
        int index=0;
        boolean isContinue=true;
        Random rd=new Random();
        while (isContinue){
            if (index+1==answerIndex){
                enLists.add(vocabTest.getEnSentence());
                index++;
            }else {
                int i=rd.nextInt(enSentences.size());
                if (!enSentences.get(i).equals(vocabTest.getEnSentence()) & !enLists.contains(enSentences.get(i))) {
                    enLists.add(enSentences.get(i));
                    index++;
                }
            }
            if (index>=4){isContinue=false;}
        }
        vocabTest.setEnSentences(enLists);
        return vocabTest;
    }


//    Lấy kết quả Test client gửi về và xử lý
    public void getVocabTestResult(List<UserTopicVocabRepo> requests){

    }








    //    Lấy list đặc tính của Vocab theo course để render ra đáp án bất kỳ
    public List<String> getCourseVocabs(Long courseId){
        return vocabRepo.findByTopics_Course_Id(courseId).stream().map(Vocab::getWord).collect(Collectors.toList());
    }
    public List<String> getCourseVnMeaning(Long courseId){
        return vocabRepo.findByTopics_Course_Id(courseId).stream().map(Vocab::getVnMeaning).collect(Collectors.toList());
    }
    public List<String> getCourseEnSentence(Long courseId){
        return vocabRepo.findByTopics_Course_Id(courseId).stream().map(Vocab::getEnSentence).collect(Collectors.toList());
    }


    //    Helper Class

    public Topic isTopicExist(Long topicId) {
        Optional<Topic> o_topic = topicRepo.findTopicById(topicId);                     // Kiểm tra topicID tồn tại không
        if (!o_topic.isPresent()) {
            throw new BadRequestException("Không tồn tại topic có Id = " + topicId);
        }
        return o_topic.get();
    }
}
