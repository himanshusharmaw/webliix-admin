package com.webliix.service.impl;

import com.webliix.model.Client;
import com.webliix.repository.ClientRepository;
import com.webliix.service.ClientService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public Optional<Client> getClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public Client createClient(Client client) {
        if (clientRepository.existsByEmail(client.getEmail())) {
            throw new RuntimeException("Client already exists with email: " + client.getEmail());
        }
        return clientRepository.save(client);
    }

    @Override
    public Client updateClient(Long id, Client client) {
        return clientRepository.findById(id).map(existing -> {
            existing.setCompanyName(client.getCompanyName());
            existing.setContactPerson(client.getContactPerson());
            existing.setEmail(client.getEmail());
            existing.setPhone(client.getPhone());
            existing.setAddress(client.getAddress());
            existing.setCity(client.getCity());
            existing.setState(client.getState());
            existing.setCountry(client.getCountry());
            existing.setZipCode(client.getZipCode());
            return clientRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Client not found with id " + id));
    }

    @Override
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}

