package com.example.myvocab.model.enummodel;


public enum OrderStatus {
    PENDING(0),ACTIVATED(1);
    private int code;

    OrderStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
