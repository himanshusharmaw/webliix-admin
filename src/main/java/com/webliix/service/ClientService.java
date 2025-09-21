package com.webliix.service;

import java.util.List;
import java.util.Optional;

import com.webliix.model.Client;

public interface ClientService {
    List<Client> getAllClients();
    Optional<Client> getClientById(Long id);
    Optional<Client> getClientByEmail(String email);
    Client createClient(Client client);
    Client updateClient(Long id, Client client);
    void deleteClient(Long id);

}
