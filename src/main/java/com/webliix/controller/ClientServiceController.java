package com.webliix.controller;

import com.webliix.dto.ClientServiceDTO;
import com.webliix.model.ClientService;
import com.webliix.service.ClientServiceService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/client-services")
public class ClientServiceController {

    private final ClientServiceService serviceService;

    public ClientServiceController(ClientServiceService serviceService) {
        this.serviceService = serviceService;
    }


    private ClientServiceDTO mapToDTO(ClientService service) {
        return new ClientServiceDTO(
        	    service.getId(),                  // Long
        	    service.getServiceName(),         // String
        	    service.getDescription(),         // String
        	    service.getPrice(),               // BigDecimal/Double
        	    service.getDurationInDays(),      // Integer
        	    service.getStatus(),              // String/Enum
        	    service.getDeadline(),            // LocalDate/LocalDateTime
        	    service.getStartFrom(),           // LocalDate/LocalDateTime
        	    service.getClient() != null ? service.getClient().getId() : null, // Long
        	    service.getCreatedAt(),           // LocalDateTime
        	    service.getUpdatedAt()            // LocalDateTime
        	);

    }

    // ✅ Get all services
    @GetMapping
    public List<ClientServiceDTO> getAllServices() {
        return serviceService.getAllServices()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ✅ Get service by ID
    @GetMapping("/{id}")
    public ResponseEntity<ClientServiceDTO> getServiceById(@PathVariable Long id) {
        Optional<ClientService> serviceOpt = serviceService.getServiceById(id);
        return serviceOpt.map(service -> ResponseEntity.ok(mapToDTO(service)))
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Get services by client ID
    @GetMapping("/client/{clientId}")
    public List<ClientServiceDTO> getServicesByClientId(@PathVariable Long clientId) {
        return serviceService.getServicesByClientId(clientId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    

    // ✅ Create new service
    @PostMapping
    public ClientServiceDTO createService(@RequestBody ClientService service) {
        ClientService savedService = serviceService.createService(service);
        return mapToDTO(savedService);
    }

    // ✅ Update service
    @PutMapping("/{id}")
    public ResponseEntity<ClientServiceDTO> updateService(@PathVariable Long id, @RequestBody ClientService service) {
        try {
            ClientService updated = serviceService.updateService(id, service);
            return ResponseEntity.ok(mapToDTO(updated));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ Delete service
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        serviceService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}
