package com.example.myvocab.model.enummodel;

public enum UserState {
    PENDING(0),ACTIVE(1),DISABLED(2);
    private int code;

    UserState(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
