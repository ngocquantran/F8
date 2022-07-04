package com.example.myvocab.model.enummodel;


import java.util.stream.Stream;

public enum OrderStatus {
    PENDING("PENDING"), ACTIVATED("ACTIVATED");

    private String code;

    OrderStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
