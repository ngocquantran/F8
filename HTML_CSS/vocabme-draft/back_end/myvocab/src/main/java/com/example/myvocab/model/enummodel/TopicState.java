package com.example.myvocab.model.enummodel;

public enum TopicState {
    PENDING("PENDING"), PASS("PASS"), NOW("NOW"), LOCK("LOCK"),WAIT("WAIT");
    private String code;

    TopicState(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
