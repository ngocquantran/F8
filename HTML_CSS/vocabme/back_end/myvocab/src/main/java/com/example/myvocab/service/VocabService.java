package com.example.myvocab.service;

import com.example.myvocab.model.Vocab;
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

    public List<Vocab> getTestVocabs(int topicId){

        List<Vocab> words=vocabRepo.getVocabs().values().stream().collect(Collectors.toList());
        Collections.shuffle(words);
        return words;
    }



}
