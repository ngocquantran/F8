package com.example.myvocab.controller;



import com.example.myvocab.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class VocabController {


//    @GetMapping("/filter-data/{topicId}")
//    public List<Vocab> getTopicVocabs(@PathVariable int topicId){
//        return vocabService.getTopicVocabs(topicId);
//    }
//
//    @GetMapping("/learning/{topicId}")
//    public List<Vocab> getStudyVocabs(@PathVariable int topicId){
//        return vocabService.getStudyVocabs(topicId);
//    }
//
//    @GetMapping("/test/{topicId}")
//    public List<VocabTest> getTestVocabs(@PathVariable int topicId){
//        return vocabService.getTestVocabs(topicId);
//    }
//
//
//    @GetMapping("/imgs/{name}")
//    public byte[] readFile(@PathVariable String name){
//        return fileService.readFile(name);
//    }
//
//    @GetMapping("/imgs")
//    public List<String> getImages(){
//        return fileService.getFiles();
//    }
}
