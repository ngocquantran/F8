package com.example.myvocab.controller;


import com.example.myvocab.model.Vocab;
import com.example.myvocab.model.VocabTest;
import com.example.myvocab.repo.VocabRepo;
import com.example.myvocab.service.FileService;
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
    @Autowired private FileService fileService;

    @GetMapping("/filter-data/{topicId}")
    public List<Vocab> getTopicVocabs(@PathVariable int topicId){
        return vocabService.getTopicVocabs(topicId);
    }

    @GetMapping("/learning/{topicId}")
    public List<Vocab> getStudyVocabs(@PathVariable int topicId){
        return vocabService.getStudyVocabs(topicId);
    }

    @GetMapping("/test/{topicId}")
    public List<VocabTest> getTestVocabs(@PathVariable int topicId){
        return vocabService.getTestVocabs(topicId);
    }


    @GetMapping("/imgs/{name}")
    public byte[] readFile(@PathVariable String name){
        return fileService.readFile(name);
    }

    @GetMapping("/imgs")
    public List<String> getImages(){
        return fileService.getFiles();
    }
}
