package com.example.myvocab.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterVocabRequest {
    private int id;
    private String word;
    private String img;
    private String type;
    private boolean isKnown;
}
