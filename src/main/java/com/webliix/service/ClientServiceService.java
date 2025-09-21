package com.webliix.service;

import com.webliix.model.ClientService;
import java.util.List;
import java.util.Optional;

public interface ClientServiceService {
    List<ClientService> getAllServices();
    Optional<ClientService> getServiceById(Long id);
    ClientService createService(ClientService service);
    ClientService updateService(Long id, ClientService service);
    void deleteService(Long id);
    List<ClientService> getServicesByClientId(Long clientId);
}
