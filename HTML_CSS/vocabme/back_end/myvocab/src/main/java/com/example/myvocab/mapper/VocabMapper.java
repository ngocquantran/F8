package com.example.myvocab.mapper;

import com.example.myvocab.model.Vocab;
import com.example.myvocab.model.VocabTest;

public class VocabMapper {
    public static VocabTest toVocabTest(Vocab vocab){
        return new VocabTest(vocab.getId(),vocab.getWord(), vocab.getImg(), vocab.getType(), vocab.getAudio(),vocab.getPhonetic(),vocab.getEnMeaning(),vocab.getVnMeaning(),vocab.getEnSentence(),vocab.getVnSentence(),vocab.getSenAudio());
    }
}
