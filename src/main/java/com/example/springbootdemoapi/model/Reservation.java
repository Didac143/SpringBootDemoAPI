package com.example.springbootdemoapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userNickname;
    private Long productId;
    private LocalDate date;
    private int quantity;

    public Reservation() {
    }

    public Reservation(String userNickname, Long productId, LocalDate date) {
        this.userNickname = userNickname;
        this.productId = productId;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(userNickname, that.userNickname)
                && Objects.equals(productId, that.productId)
                && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userNickname, productId, date);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "userNickname='" + userNickname + '\'' +
                ", productId=" + productId +
                ", date=" + date +
                ", quantity=" + quantity +
                '}';
    }
}
