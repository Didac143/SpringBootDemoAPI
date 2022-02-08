package com.example.springbootdemoapi.unit_test.client;

import com.example.springbootdemoapi.model.Client;
import com.example.springbootdemoapi.model.exception.RequestException;
import com.example.springbootdemoapi.model.vo.ClientVO;
import com.example.springbootdemoapi.repository.ClientRepository;
import com.example.springbootdemoapi.service.ClientService;
import com.example.springbootdemoapi.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClientServiceTest {
    private final ClientRepository clientRepository;
    private final ClientService clientService;

    public ClientServiceTest() {
        this.clientRepository = mock(ClientRepository.class);
        this.clientService = new ClientServiceImpl(clientRepository);
    }

    @Test
    void getAllClientsTest() {
        //  GIVEN =
        String clientName1 = "Ikea";
            List<Client> clients = new ArrayList<>();
            clients.add(new Client(clientName1));
            clients.add(new Client("MediaMarkt"));

            Map<String, String> params = new HashMap<>();

            List<Client> filteredClients = clients.stream()
                    .filter(c ->
                            c.getName().equalsIgnoreCase(clientName1)
                    ).collect(Collectors.toList());

            when(clientRepository.findAll()).thenReturn(clients);
            when(clientRepository.findClientByNameIgnoreCase(null)).thenReturn(clients);
            when(clientRepository.findClientByNameIgnoreCase(clientName1)).thenReturn(filteredClients);

            assertEquals(clientService.getAllClients(null), clients);
            assertEquals(clients, clientService.getAllClients(params));

            params.put("test", "test");
            assertEquals(clients, clientService.getAllClients(params));

            params.put("clientName", clientName1);
            assertEquals(filteredClients, clientService.getAllClients(params));
    }

    @Test
    void addNewClientTest() {
        //  GIVEN =
        String clientName = "clientName1";
        Long clientId = 125L;
        ClientVO clientVOtoSave = new ClientVO(clientName);
        Client clientSaved = new Client(clientName);
        clientSaved.setId(clientId);

        when(clientRepository.existsClientByNameIgnoreCase(clientVOtoSave.getName())).thenReturn(false);
        when(clientRepository.save(any(Client.class))).thenReturn(clientSaved);

        //WHEN -> executing business logic
        Client clientReturned = clientService.addClient(clientVOtoSave);

        //THEN -> asserts
        assertNotNull(clientReturned);
        assertEquals(clientId, clientReturned.getId());
        assertEquals(clientName, clientReturned.getName());
    }

    @Test
    void addClientWithExistingName() {
        //  GIVEN =
        String clientName = "clientName1";
        ClientVO clientVOtoSave = new ClientVO(clientName);

        when(clientRepository.existsClientByNameIgnoreCase(clientVOtoSave.getName())).thenReturn(true);

        //WHEN -> executing business logic

        //THEN -> throws RequestException
        assertThrows(RequestException.class, () -> clientService.addClient(clientVOtoSave));
    }

}
