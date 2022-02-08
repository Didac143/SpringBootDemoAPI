package com.example.springbootdemoapi.controller;

import com.example.springbootdemoapi.model.Client;
import com.example.springbootdemoapi.model.vo.ClientVO;
import com.example.springbootdemoapi.service.impl.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientServiceImpl clientService;

    @Autowired
    public ClientController(ClientServiceImpl clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getAllClients(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(clientService.getAllClients(params), HttpStatus.OK);
    }

    @GetMapping("{clientId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getClientById(@PathVariable Long clientId) {
        return new ResponseEntity<>(clientService.getClientById(clientId), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> addClient(@Valid @RequestBody ClientVO clientVO) {
        return new ResponseEntity<>(clientService.addClient(clientVO), HttpStatus.CREATED);
    }

    @PatchMapping("{clientId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Client> updateClient(@Valid @RequestBody ClientVO clientVO,
                                               @PathVariable Long clientId){
        return new ResponseEntity<>(clientService.updateClient(clientVO, clientId), HttpStatus.OK);
    }

    @DeleteMapping("{clientId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> deleteClient(@PathVariable Long clientId) {
        return new ResponseEntity<>(clientService.deleteClient(clientId), HttpStatus.OK);
    }



}
