package com.webliix.controller;

import com.webliix.dto.ClientDTO;
import com.webliix.model.Client;
import com.webliix.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.webliix.service.MailService;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "http://localhost:5173")
public class ClientController {

    private ClientService clientService;
    private final MailService mailService;

	@Autowired
    public ClientController(ClientService clientService, MailService mailService) {
        this.clientService = clientService;
        this.mailService = mailService;
    }
	
    @GetMapping
    public List<ClientDTO> getAllClients() {
        return clientService.getAllClients()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
        Optional<Client> client = clientService.getClientById(id);
        return client.map(value -> ResponseEntity.ok(convertToDTO(value)))
                     .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ClientDTO createClient(@RequestBody Client client,
                                  @RequestParam(name = "sendWelcome", required = false, defaultValue = "false") boolean sendWelcome) {
    		Client savedClient = clientService.createClient(client);
    	
    		ClientDTO dto = convertToDTO(savedClient);

        if (sendWelcome) {
            System.out.println("Sending Mail........................................");
            mailService.sendWelcomeEmailAsync(savedClient);
            System.out.println("Mail Sent");
        }

        return dto;
    }


    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id, @RequestBody Client clientDetails) {
        try {
            Client updatedClient = clientService.updateClient(id, clientDetails);
            return ResponseEntity.ok(convertToDTO(updatedClient));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.ok().build();
    }

    private ClientDTO convertToDTO(Client client) {
        ClientDTO dto = new ClientDTO();
        dto.setId(client.getId());
        dto.setCompanyName(client.getCompanyName());
        dto.setContactPerson(client.getContactPerson());
        dto.setEmail(client.getEmail());
        dto.setPhone(client.getPhone());
        dto.setAddress(client.getAddress());
        dto.setCity(client.getCity());
        dto.setState(client.getState());
        dto.setCountry(client.getCountry());
        dto.setZipCode(client.getZipCode());
        dto.setCreatedAt(client.getCreatedAt());
        dto.setUpdatedAt(client.getUpdatedAt());
        return dto;
    }
}

