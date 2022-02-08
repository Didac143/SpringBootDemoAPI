package com.example.springbootdemoapi.model;

import com.example.springbootdemoapi.model.vo.ReservationVO;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Reservation> reservations;

    public Cart() {
        this.reservations = new ArrayList<>();
    }

    public void addItem(Reservation reservation) {
        this.reservations.add(reservation);
    }

    public void removeItem(Reservation reservation) {
        this.reservations.remove(reservation);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
