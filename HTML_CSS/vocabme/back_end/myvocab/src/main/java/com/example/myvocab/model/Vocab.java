package com.example.myvocab.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vocab {
    private int id;
    private String word;
    private String img;
    private String type;
    private String audio;
    private String phonetic;
    private String enMeaning;
    private String vnMeaning;
    private String enSentence;
    private String vnSentence;
    private String senAudio;
}
