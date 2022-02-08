package com.example.springbootdemoapi.service;

import com.example.springbootdemoapi.model.Client;
import com.example.springbootdemoapi.model.vo.ClientVO;

import java.util.Map;

public interface ClientService {
    Iterable<Client> getAllClients(Map<String, String> params);
    Client getClientById(Long clientId);
    Client addClient(ClientVO clientVO);
    Client updateClient(ClientVO clientVO, Long clientId);
    Client deleteClient(Long clientId);
}
