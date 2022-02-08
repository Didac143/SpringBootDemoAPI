package com.example.springbootdemoapi.model.vo;

import com.example.springbootdemoapi.model.Reservation;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class ReservationVO {

    @NotBlank
    private String userNickname;
    @NotNull
    private Long productId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;
    @Min(1)
    private int quantity;

    public ReservationVO() {
    }

    public ReservationVO(String userNickname, Long productId, LocalDate date, int quantity) {
        this.userNickname = userNickname;
        this.productId = productId;
        this.date = date;
        this.quantity = quantity;
    }

    public Reservation toReservation() {
        Reservation reservation = new Reservation(userNickname, productId, date);
        reservation.setQuantity(quantity);
        return reservation;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ReservationVO{" +
                "userNickname='" + userNickname + '\'' +
                ", productId=" + productId +
                ", date=" + date +
                ", quantity=" + quantity +
                '}';
    }
}
