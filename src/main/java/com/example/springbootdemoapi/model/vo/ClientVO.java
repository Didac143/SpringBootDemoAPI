package com.example.springbootdemoapi.model.vo;

import com.example.springbootdemoapi.model.Client;

import javax.validation.constraints.NotBlank;

public class ClientVO {
    @NotBlank
    private String name;

    public ClientVO() {
    }

    public ClientVO(String name) {
        this.name = name;
    }

    public Client toClient() {
        return new Client(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
