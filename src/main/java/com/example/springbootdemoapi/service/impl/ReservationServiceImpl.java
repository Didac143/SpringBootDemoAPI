package com.example.springbootdemoapi.service.impl;

import com.example.springbootdemoapi.model.Reservation;
import com.example.springbootdemoapi.model.exception.RequestException;
import com.example.springbootdemoapi.model.vo.ReservationVO;
import com.example.springbootdemoapi.repository.ReservationRepository;
import com.example.springbootdemoapi.service.ProductService;
import com.example.springbootdemoapi.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ProductService productService;

    @Autowired
    public ReservationServiceImpl(ReservationRepository repository, ProductService productService) {
        this.reservationRepository = repository;
        this.productService = productService;
    }

    public Reservation addReservation(ReservationVO reservationVO) {
        Long productId = reservationVO.getProductId();

        productService.getProductById(productId);

        Reservation reservation = new Reservation(reservationVO.getUserNickname(), productId, reservationVO.getDate());
        reservation.setQuantity(reservationVO.getQuantity());

        return reservationRepository.save(reservation);
    }

    public List<Reservation> getAllReservations() {
        return (List<Reservation>) reservationRepository.findAll();
    }

    public Reservation getReservationById(Long reservationId) {
        Assert.notNull(reservationId, "Provided reservation id is null");
        return reservationRepository.findById(reservationId).orElseThrow(() -> new RequestException("Reservation not found"));
    }

    public Reservation updateReservation(Long reservationId, ReservationVO reservationVO) {
        Reservation reservation = getReservationById(reservationId);

        if (reservationVO.getUserNickname() != null) reservation.setUserNickname(reservationVO.getUserNickname());
        if (reservationVO.getDate() != null) reservation.setDate(reservationVO.getDate());
        if (reservationVO.getQuantity() != 0) reservation.setQuantity(reservationVO.getQuantity());
        if (reservationVO.getProductId() != null) {
            productService.getProductById(reservationVO.getProductId());
            reservation.setProductId(reservationVO.getProductId());
        }

        return reservationRepository.save(reservation);
    }

    public Reservation deleteReservation(Long reservationId) {
        Reservation deletedReservation = getReservationById(reservationId);
        reservationRepository.delete(deletedReservation);
        return deletedReservation;
    }

}
