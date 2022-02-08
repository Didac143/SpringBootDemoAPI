package com.example.springbootdemoapi.integration_test.client;

import com.example.springbootdemoapi.model.Client;
import com.example.springbootdemoapi.model.exception.RequestException;
import com.example.springbootdemoapi.model.vo.ClientVO;
import com.example.springbootdemoapi.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ClientServiceIntegrationTest {

    @Autowired
    private ClientService clientService;

    @Test
    void getAllClients() {
        ClientVO clientVO1 = new ClientVO("Client1");
        ClientVO clientVO2 = new ClientVO("Client2");
        ClientVO clientVO3 = new ClientVO("Client3");

        Client savedClient1 = clientService.addClient(clientVO1);
        Client savedClient2 = clientService.addClient(clientVO2);
        Client savedClient3 = clientService.addClient(clientVO3);

        List<Client> expectedClients = (List<Client>) clientService.getAllClients(null);

        assertNotNull(expectedClients);
        assertTrue(expectedClients.contains(savedClient1));
        assertTrue(expectedClients.contains(savedClient2));
        assertTrue(expectedClients.contains(savedClient3));
    }

    @Test
    void getClientsByExistingName() {
        String clientName = "Client1";
        ClientVO clientVO1 = new ClientVO(clientName);

        Client savedClient1 = clientService.addClient(clientVO1);

        Map<String, String> params = new HashMap<>();
        params.put("clientName", clientName);
        params.put("shouldBeIgnored", "testing");

        List<Client> expectedClients = (List<Client>) clientService.getAllClients(params);

        assertNotNull(expectedClients);
        assertEquals(1, expectedClients.size());
        assertTrue(expectedClients.contains(savedClient1));
    }

    @Test
    void getClientsByNotExistingName() {
        String clientName = "Client1";
        ClientVO clientVO1 = new ClientVO(clientName);

        Client savedClient1 = clientService.addClient(clientVO1);

        Map<String, String> params = new HashMap<>();
        params.put("clientName", "Client2");
        params.put("shouldBeIgnored", "testing");

        List<Client> expectedClients = (List<Client>) clientService.getAllClients(params);

        assertNotNull(expectedClients);
        assertEquals(0, expectedClients.size());
    }

    @Test
    void getExistingClientById() {
        ClientVO clientVO = new ClientVO("Client1");
        Client savedClient = clientService.addClient(clientVO);
        Long clientId = savedClient.getId();

        assertNotNull(savedClient);
        assertEquals(savedClient, clientService.getClientById(clientId));
    }

    @Test
    void getNotExistingClientById() {
        assertThrows(RequestException.class, () -> clientService.getClientById(null));
        assertThrows(RequestException.class, () -> clientService.getClientById(1L));
        assertThrows(RequestException.class, () -> clientService.getClientById(-1L));
    }

    @Test
    void addClientWithValidName() {
        ClientVO clientToSave = new ClientVO("Client1");

        Client savedClient = clientService.addClient(clientToSave);

        assertNotNull(savedClient.getId());
        assertEquals(clientToSave.getName(), savedClient.getName());
    }

    @Test
    void addClientWithNotValidName() {
        String clientName = "Client1";
        ClientVO clientToSave1 = new ClientVO(clientName);
        ClientVO clientToSave2 = new ClientVO(clientName);

        clientService.addClient(clientToSave1);

        assertThrows(RequestException.class, () -> clientService.addClient(clientToSave2));
    }

    @Test
    void updateExistingClient() {
        String originalName = "Client1", updatedName = "Client2";
        ClientVO clientToSave = new ClientVO(originalName);
        ClientVO clientToUpdate = new ClientVO(updatedName);

        Client savedClient = clientService.addClient(clientToSave);
        Client updatedClient = clientService.updateClient(clientToUpdate, savedClient.getId());

        assertNotNull(updatedClient);
        assertEquals(updatedName, updatedClient.getName());
        assertEquals(savedClient.getId(), updatedClient.getId());
    }

    @Test
    void updateNotExistingClient() {
        ClientVO clientVO = new ClientVO("Client1");

        assertThrows(RequestException.class, () -> clientService.updateClient(clientVO, null));
        assertThrows(RequestException.class, () -> clientService.updateClient(clientVO, 1L));
        assertThrows(RequestException.class, () -> clientService.updateClient(clientVO, -1L));
    }

    @Test
    void updateExistingClientWithNotValidName() {
        String clientName = "Client1";
        ClientVO clientVOtoSave = new ClientVO(clientName);
        ClientVO clientVOtoUpdate = new ClientVO(clientName);

        Client savedClient = clientService.addClient(clientVOtoSave);

        assertThrows(RequestException.class, () -> clientService.updateClient(clientVOtoUpdate, savedClient.getId()));
    }

    @Test
    void deleteExistingClient() {
        Client savedClient = clientService.addClient(new ClientVO("Client1"));
        Long savedClientId = savedClient.getId();

        assertEquals(savedClient, clientService.deleteClient(savedClientId));
        assertThrows(RequestException.class, () -> clientService.getClientById(savedClientId));
    }

    @Test
    void deleteNotExistingClient() {
        //assertThrows(RequestException.class, () -> clientService.deleteClient(null));
        assertThrows(RequestException.class, () -> clientService.deleteClient(1L));
        assertThrows(RuntimeException.class, () -> clientService.deleteClient(-1L));
    }
}
