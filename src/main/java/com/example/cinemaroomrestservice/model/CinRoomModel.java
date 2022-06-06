package com.example.cinemaroomrestservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CinRoomModel {

    @JsonProperty("total_rows")
    private final int TOTAL_ROWS;

    @JsonProperty("total_columns")
    private final int TOTAL_COLUMNS;

    @JsonProperty("available_seats")
    private final ArrayList<Seat> CINEMA_ROOM = new ArrayList<>();

    @JsonIgnore
    private final Map<String, Seat> purchaseTicketsMap = new ConcurrentHashMap<>();

    private void initializeCinemaRoom() {
        for (int i = 1; i <= TOTAL_ROWS; i++) {

            for (int j = 1; j <= TOTAL_COLUMNS; j++) {
                CINEMA_ROOM.add(new Seat(i, j));
            }
        }
    }

    public CinRoomModel(int TOTAL_ROWS, int TOTAL_COLUMNS) {
        this.TOTAL_ROWS = TOTAL_ROWS;
        this.TOTAL_COLUMNS = TOTAL_COLUMNS;
        initializeCinemaRoom();
    }

    public int getTotal_rows() {
        return TOTAL_ROWS;
    }

    public int getTotal_columns() {
        return TOTAL_COLUMNS;
    }

    @JsonIgnore
    public ArrayList<Seat> getCINEMA_ROOM() {
        return CINEMA_ROOM;
    }

    public ArrayList<Seat> getAvailableSeats() {
        ArrayList<Seat> availableSeatsRoom = new ArrayList<>();
        for (Seat seat : CINEMA_ROOM) {
            if (seat.isAvailable()) {
                availableSeatsRoom.add(seat);
            }
        }
        return availableSeatsRoom;
    }

    public boolean isSeatAvailable(int row, int column) {
        boolean exists = false;
        for (Seat seat : CINEMA_ROOM) {
            if (seat.getRow() == row && seat.getColumn() == column && seat.isAvailable()) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    public String buyTicket(int row, int column) {
        for (Seat seat : CINEMA_ROOM) {
            if (seat.getRow() == row && seat.getColumn() == column) {
                seat.setAvailable(false);
                break;
            }
        }

        String token = UUID.randomUUID().toString();
        purchaseTicketsMap.put(token, new Seat(row, column));

        return token;
    }

    public boolean containsToken(String token) {
        return purchaseTicketsMap.containsKey(token);
    }

    public Seat returnTicketByToken(String token) {
        Seat seat = purchaseTicketsMap.get(token);
        for (Seat other : CINEMA_ROOM) {
            if (other.getRow() == seat.getRow() && other.getColumn() == seat.getColumn()) {
                other.setAvailable(true);
                break;
            }
        }
        return seat;
    }


}