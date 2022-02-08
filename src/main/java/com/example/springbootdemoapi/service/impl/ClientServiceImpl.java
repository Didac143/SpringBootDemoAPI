package com.example.springbootdemoapi.service.impl;

import com.example.springbootdemoapi.model.Client;
import com.example.springbootdemoapi.model.exception.RequestException;
import com.example.springbootdemoapi.model.vo.ClientVO;
import com.example.springbootdemoapi.repository.ClientRepository;
import com.example.springbootdemoapi.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Map;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Iterable<Client> getAllClients(Map<String, String> params) {
        if (CollectionUtils.isEmpty(params)) return clientRepository.findAll();

        String clientName = null;

        if (params.containsKey("clientName")) clientName = params.get("clientName");

        return clientRepository.findClientByNameIgnoreCase(clientName);
    }

    public Client getClientById(Long clientId) {
        return clientRepository.findById(clientId).orElseThrow(() -> new RequestException("Client not found"));
    }

    public Client addClient(ClientVO clientVO) {
        if (clientRepository.existsClientByNameIgnoreCase(clientVO.getName()))
            throw new RequestException("Name is already registered");
        return clientRepository.save(clientVO.toClient());
    }

    public Client updateClient(ClientVO clientVO, Long clientId) {
        Assert.notNull(clientVO, "ClientVO is null");
        Client client = getClientById(clientId);
        if (clientRepository.existsClientByNameIgnoreCase(clientVO.getName()))
            throw new RequestException("Name is already registered");
        client.setName(clientVO.getName());
        return clientRepository.save(client);
    }

    public Client deleteClient(Long clientId) {
        Client deleted = getClientById(clientId);
        clientRepository.delete(deleted);
        return deleted;
    }

}
