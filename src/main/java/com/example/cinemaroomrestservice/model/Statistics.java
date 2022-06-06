package com.example.cinemaroomrestservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Statistics {

    @JsonProperty("current_income")
    private int currentIncome = 0;

    @JsonProperty("number_of_available_seats")
    private int numberOfAvailableSeats;

    @JsonProperty("number_of_purchased_tickets")
    private int numberOfPurchasedTickets = 0;


    public Statistics(int numberOfAvailableSeats) {
        this.numberOfAvailableSeats = numberOfAvailableSeats;
    }

    public int getCurrentIncome() {
        return currentIncome;
    }

    public void setCurrentIncome(int currentIncome) {
        this.currentIncome = currentIncome;
    }

    public int getNumberOfAvailableSeats() {
        return numberOfAvailableSeats;
    }

    public void setNumberOfAvailableSeats(int numberOfAvailableSeats) {
        this.numberOfAvailableSeats = numberOfAvailableSeats;
    }

    public int getNumberOfPurchasedTickets() {
        return numberOfPurchasedTickets;
    }

    public void setNumberOfPurchasedTickets(int numberOfPurchasedTickets) {
        this.numberOfPurchasedTickets = numberOfPurchasedTickets;
    }


}
