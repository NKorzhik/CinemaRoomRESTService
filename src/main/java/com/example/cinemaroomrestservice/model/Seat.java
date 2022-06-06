package com.example.cinemaroomrestservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Seat {
    private final int row;
    private final int column;
    private final int price;
    private boolean available = true;

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        price = row <= 4 ? 10 : 8;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public int getPrice() {
        return price;
    }

    @JsonIgnore
    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }


}