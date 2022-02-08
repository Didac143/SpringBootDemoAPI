package com.example.springbootdemoapi.model.vo;

import javax.validation.constraints.Min;

public class CartItemVO {

    private Long reservationId;


    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }
}
