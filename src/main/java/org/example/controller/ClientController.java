package org.example.controller;

import org.example.model.Client;
import org.example.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @PostMapping("/card-request")
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client savedClient = clientService.createClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Client>> getAll() {
        List<Client> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{oib}")
    public ResponseEntity<Client> getClientByOib(@PathVariable String oib) {
        Optional<Client> client = clientService.getClientByOib(oib);
        return client.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{oib}")
    public ResponseEntity<Void> deleteClientByOib(@PathVariable String oib) {
        return clientService.getClientByOib(oib)
                .map(client -> {
                    clientService.deleteClientByOib(oib);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/edit-status/{oib}")
    public ResponseEntity<Client> editStatus(@PathVariable String oib,@RequestBody Client cardStatus) {
        return clientService.getClientByOib(oib)
                .map(existingClient -> {
                    Client updatedClient = clientService.editStatus(oib, cardStatus);
                    return ResponseEntity.status(HttpStatus.OK).body(updatedClient);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
