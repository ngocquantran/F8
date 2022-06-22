package com.example.myvocab.service;

import com.example.myvocab.mapper.VocabMapper;
import com.example.myvocab.model.Vocab;
import com.example.myvocab.model.VocabTest;
import com.example.myvocab.repo.VocabRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class VocabService {
    @Autowired
    private VocabRepo vocabRepo;

    public List<Vocab> getTopicVocabs(int topicId){
        return vocabRepo.getVocabs().values().stream().toList();
    }

    public List<Vocab> getStudyVocabs(int topicId){
        return vocabRepo.getVocabs().values().stream().toList();
    }

    public List<VocabTest> getTestVocabs(int topicId){
        List<VocabTest> words=vocabRepo.getVocabs().values()
                .stream()
                .map(vocab -> VocabMapper.toVocabTest(vocab))
                .map(vocabTest -> renderVocabAnswers(vocabTest,topicId))
                .map(vocabTest -> renderVnMeaningAnswers(vocabTest,topicId))
                .map(vocabTest -> renderEnSentenceAnswers(vocabTest,topicId))
                .collect(Collectors.toList());
        Collections.shuffle(words);

        return words;
    }

//    Render answer list for VocabTest (Choosing 4 options)

    public VocabTest renderVocabAnswers(VocabTest vocabTest, int topicId) {
        int answerIndex = vocabTest.getAnswerIndex();
        List<String> vocabs = getCourseVocabs(topicId);

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


    public VocabTest renderVnMeaningAnswers(VocabTest vocabTest,int topicId) {
        int answerIndex = vocabTest.getAnswerIndex();
        List<String> vnMeanings=getCourseVnMeaning(topicId);
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

    public VocabTest renderEnSentenceAnswers(VocabTest vocabTest,int topicId) {
        int answerIndex = vocabTest.getAnswerIndex();
        List<String> enSentences=getCourseEnSentence(topicId);
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


//    Lấy list đặc tính của Vocab theo course
    public List<String> getCourseVocabs(int courseId){
        return vocabRepo.getVocabs().values().stream().map(Vocab::getWord).collect(Collectors.toList());
    }
    public List<String> getCourseVnMeaning(int courseId){
        return vocabRepo.getVocabs().values().stream().map(Vocab::getVnMeaning).collect(Collectors.toList());
    }
    public List<String> getCourseEnSentence(int courseId){
        return vocabRepo.getVocabs().values().stream().map(Vocab::getEnSentence).collect(Collectors.toList());
    }



}
