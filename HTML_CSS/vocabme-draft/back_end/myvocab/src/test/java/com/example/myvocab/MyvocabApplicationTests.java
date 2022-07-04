package com.example.myvocab;

import com.example.myvocab.dto.VocabsForChoosing;
import com.example.myvocab.model.enummodel.LearningStage;
import com.example.myvocab.repo.UserTopicVocabRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MyvocabApplicationTests {
    @Autowired private
    UserTopicVocabRepo userTopicVocabRepo;

    @Test
    void get_choose_word_list(){
        List<VocabsForChoosing> list=userTopicVocabRepo.getVocabsForChoosing(4L, LearningStage.FIRST);
        list.forEach(System.out::println);

    }

}
