package com.example.myvocab.model.enummodel;

public enum TopicState {
    PENDING(0), PASS(1), NOW(2), LOCK(3);
    private int code;

    TopicState(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
