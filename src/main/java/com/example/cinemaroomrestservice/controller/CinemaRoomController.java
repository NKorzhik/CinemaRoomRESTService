package com.example.cinemaroomrestservice.controller;

import com.example.cinemaroomrestservice.model.CinRoomModel;
import com.example.cinemaroomrestservice.model.PurchaseTicket;
import com.example.cinemaroomrestservice.model.Seat;
import com.example.cinemaroomrestservice.model.Statistics;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class CinemaRoomController {
    private final CinRoomModel cinema_room = new CinRoomModel(9, 9);

    private final Statistics stat = new Statistics(cinema_room.getTotal_columns() * cinema_room.getTotal_rows());

    @GetMapping("/seats")
    private CinRoomModel getCinemaRoom() {
        return cinema_room;
    }

    @PostMapping("/purchase")
    private ResponseEntity<?> postPurchase(@RequestBody Seat seat) {
        int row = seat.getRow();
        int column = seat.getColumn();
        if (row > cinema_room.getTotal_rows() || row < 0 || column > cinema_room.getTotal_columns() || column < 0) {
            return new ResponseEntity<>(Map.of("error", "The number of a row or a column is out of bounds!"), HttpStatus.BAD_REQUEST);
        } else {
            if (cinema_room.isSeatAvailable(row, column)) {
                String token = cinema_room.buyTicket(row, column);
                PurchaseTicket purchase = new PurchaseTicket(token, seat);
                stat.setCurrentIncome(stat.getCurrentIncome() + seat.getPrice());
                stat.setNumberOfAvailableSeats(stat.getNumberOfAvailableSeats() - 1);
                stat.setNumberOfPurchasedTickets(stat.getNumberOfPurchasedTickets() + 1);
                return new ResponseEntity<>(purchase, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Map.of("error", "The ticket has been already purchased!"), HttpStatus.BAD_REQUEST);
            }
        }
    }

    @PostMapping("/return")
    private ResponseEntity<?> returnTicket(@RequestBody Map<String, String> token) {
        String id = token.get("token");
        if (cinema_room.containsToken(id)) {
            Seat ticket = cinema_room.returnTicketByToken(id);
            return new ResponseEntity<>(Map.of("returned_ticket", ticket), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Map.of("error", "Wrong token!"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/stats")
    private ResponseEntity<?> postStatistic(@RequestParam(required = false) String password) {
        if (!password.equals("super_secret")) {
            return new ResponseEntity<>(Map.of("error", "The password is wrong!"), HttpStatus.UNAUTHORIZED);
        } else return new ResponseEntity<>(stat, HttpStatus.OK);
    }

}