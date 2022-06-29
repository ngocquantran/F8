package com.example.myvocab.model.enummodel;


public enum LearningStage {
    FIRST("F"),BEST("B"),PREVIOUS("P"),NOW("N");
    private String code;

    LearningStage(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
