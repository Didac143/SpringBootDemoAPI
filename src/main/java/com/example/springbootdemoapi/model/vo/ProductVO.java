package com.example.springbootdemoapi.model.vo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

public class ProductVO {

    @NotBlank
    private String name;
    @Min(0)
    private BigDecimal price;
    private Long clientId;

    public ProductVO() {
    }

    public ProductVO(String name, BigDecimal price, Long clientId) {
        this.name = name;
        this.price = price;
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public @Min(0) BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "ProductVO{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", clientId=" + clientId +
                '}';
    }
}
