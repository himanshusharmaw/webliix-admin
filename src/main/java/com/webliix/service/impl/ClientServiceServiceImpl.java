package com.webliix.service.impl;

import org.springframework.stereotype.Service;

import com.webliix.model.ClientService;
import com.webliix.repository.ClientServiceRepository;
import com.webliix.service.ClientServiceService;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceServiceImpl implements ClientServiceService {

    private final ClientServiceRepository serviceRepository;

    public ClientServiceServiceImpl(ClientServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public List<ClientService> getAllServices() {
        return serviceRepository.findAll();
    }

    @Override
    public Optional<ClientService> getServiceById(Long id) {
        return serviceRepository.findById(id);
    }

    @Override
    public ClientService createService(ClientService service) {
        return serviceRepository.save(service);
    }

    @Override
    public ClientService updateService(Long id, ClientService service) {
        return serviceRepository.findById(id).map(existing -> {
            if (service.getServiceName() != null) {
                existing.setServiceName(service.getServiceName());
            }
            if (service.getDescription() != null) {
                existing.setDescription(service.getDescription());
            }
            if (service.getPrice() != null) {
                existing.setPrice(service.getPrice());
            }
            if (service.getDurationInDays() != null) {
                existing.setDurationInDays(service.getDurationInDays());
            }
            if (service.getClient() != null) {
                existing.setClient(service.getClient());
            }

            return serviceRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Service not found with id " + id));
    }


    @Override
    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }
    
    @Override
    public List<ClientService> getServicesByClientId(Long clientId) {
        return serviceRepository.findByClientId(clientId);
    }
    
    

}
