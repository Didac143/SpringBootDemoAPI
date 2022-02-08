package com.example.springbootdemoapi.service;

import com.example.springbootdemoapi.model.Reservation;
import com.example.springbootdemoapi.model.vo.ReservationVO;

import java.util.List;

public interface ReservationService {
    Reservation addReservation(ReservationVO reservationVO);
    List<Reservation> getAllReservations();
    Reservation getReservationById(Long reservationId);
    Reservation updateReservation(Long reservationId, ReservationVO reservationVO);
    Reservation deleteReservation(Long reservationId);
}
