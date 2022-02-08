package com.example.springbootdemoapi.controller;

import com.example.springbootdemoapi.model.Reservation;
import com.example.springbootdemoapi.model.vo.ReservationVO;
import com.example.springbootdemoapi.service.impl.ReservationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationServiceImpl reservationService;

    @Autowired
    public ReservationController(ReservationServiceImpl reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@Valid @RequestBody ReservationVO reservationVO) {
        return new ResponseEntity<>(reservationService.addReservation(reservationVO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return new ResponseEntity<>(reservationService.getAllReservations(), HttpStatus.OK);
    }

    @GetMapping("{reservationId}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long reservationId) {
        return new ResponseEntity<>(reservationService.getReservationById(reservationId), HttpStatus.OK);
    }

    @PatchMapping("{reservationId}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long reservationId,
                                                         @RequestBody ReservationVO reservationVO) {
        return new ResponseEntity<>(reservationService.updateReservation(reservationId, reservationVO), HttpStatus.OK);
    }

    @DeleteMapping("{reservationId}")
    public ResponseEntity<Reservation> deleteReservation(@PathVariable Long reservationId) {
        return new ResponseEntity<>(reservationService.deleteReservation(reservationId), HttpStatus.OK);
    }


}
