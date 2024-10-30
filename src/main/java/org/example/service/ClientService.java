package org.example.service;

import org.example.model.Client;
import org.example.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;

    public Client createClient(Client client){
        return clientRepository.save(client);
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClientByOib(String oib) {
        return clientRepository.findByOib(oib);
    }

    public void deleteClientByOib(String oib) {
        Optional<Client> client = clientRepository.findByOib(oib);
        client.ifPresent(value -> clientRepository.delete(value));
    }

    public Client editStatus(String oib, Client cardStatus) {
        Optional<Client> client = clientRepository.findByOib(oib);
        if (client.isPresent()) {
            client.get().setCardStatus(cardStatus.getCardStatus());
            return clientRepository.save(client.get());
        }
        return null;
    }
}
