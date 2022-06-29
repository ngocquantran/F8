package com.example.myvocab.model.enummodel;

public enum Gender {
    MALE("Nam"),FEMALE("Nữ");
    private String code;

    Gender(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
