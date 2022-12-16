package com.cps3230assignment.models;

public enum AlertType {
    CAR (1),
    BOAT (2),
    PROPERTY_FOR_RENT(3),
    PROPERTY_FOR_SALE(4),
    TOYS(5),
    ELECTRONICS(6);

    private final int value;

    AlertType(int value){
        this.value = value;
    }

    public int value() { return value; }

}
