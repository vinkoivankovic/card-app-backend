package org.example.controller;

import org.example.model.CardStatus;
import org.example.model.Client;
import org.example.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClientControllerTest {

    @InjectMocks
    private ClientController clientController;

    @Mock
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createClient_ShouldReturnCreatedClient() {
        Client client = new Client();
        client.setId(1L);
        client.setFirstName("John");
        client.setLastName("Doe");
        client.setOib("12345678903");
        client.setCardStatus(CardStatus.PENDING);

        when(clientService.createClient(any(Client.class))).thenReturn(client);

        ResponseEntity<Client> response = clientController.createClient(client);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(client, response.getBody());
    }

    @Test
    void getAll_ShouldReturnListOfClients() {
        List<Client> clients = new ArrayList<>();
        clients.add(new Client());

        when(clientService.getAllClients()).thenReturn(clients);

        ResponseEntity<List<Client>> response = clientController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clients, response.getBody());
    }

    @Test
    void getClientByOib_ShouldReturnClient_WhenExists() {
        Client client = new Client();
        client.setOib("12345678901");

        when(clientService.getClientByOib("12345678901")).thenReturn(Optional.of(client));

        ResponseEntity<Client> response = clientController.getClientByOib("12345678901");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(client, response.getBody());
    }

    @Test
    void getClientByOib_ShouldReturnNotFound_WhenDoesNotExist() {
        when(clientService.getClientByOib("12345678901")).thenReturn(Optional.empty());

        ResponseEntity<Client> response = clientController.getClientByOib("12345678901");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteClientByOib_ShouldReturnNoContent_WhenDeleted() {
        when(clientService.getClientByOib("12345678901")).thenReturn(Optional.of(new Client()));

        ResponseEntity<Void> response = clientController.deleteClientByOib("12345678901");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(clientService, times(1)).deleteClientByOib("12345678901");
    }

    @Test
    void deleteClientByOib_ShouldReturnNotFound_WhenDoesNotExist() {
        when(clientService.getClientByOib("12345678901")).thenReturn(Optional.empty());

        ResponseEntity<Void> response = clientController.deleteClientByOib("12345678901");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void editStatus_ShouldReturnUpdatedClient_WhenExists() {
        Client client = new Client();
        client.setOib("12345678901");
        Client updatedClient = new Client();
        updatedClient.setCardStatus(CardStatus.PENDING);

        when(clientService.getClientByOib("12345678901")).thenReturn(Optional.of(client));
        when(clientService.editStatus(eq("12345678901"), any(Client.class))).thenReturn(updatedClient);

        ResponseEntity<Client> response = clientController.editStatus("12345678901", updatedClient);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedClient, response.getBody());
    }

    @Test
    void editStatus_ShouldReturnNotFound_WhenDoesNotExist() {
        when(clientService.getClientByOib("12345678901")).thenReturn(Optional.empty());

        ResponseEntity<Client> response = clientController.editStatus("12345678901", new Client());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}