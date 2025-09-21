package com.webliix.controller;

import com.webliix.model.Client;
import com.webliix.model.Document;
import com.webliix.model.Invoice;
import com.webliix.model.ClientService;
import com.webliix.repository.ClientRepository;
import com.webliix.repository.DocumentRepository;
import com.webliix.repository.InvoiceRepository;
import com.webliix.repository.ClientServiceRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class SearchController {

    private final ClientRepository clientRepo;
    private final InvoiceRepository invoiceRepo;
    private final DocumentRepository documentRepo;
    private final ClientServiceRepository serviceRepo;

    public SearchController(ClientRepository clientRepo, InvoiceRepository invoiceRepo,
                            DocumentRepository documentRepo, ClientServiceRepository serviceRepo) {
        this.clientRepo = clientRepo;
        this.invoiceRepo = invoiceRepo;
        this.documentRepo = documentRepo;
        this.serviceRepo = serviceRepo;
    }

    @GetMapping("/api/search")
    public Map<String, List<Map<String, String>>> search(@RequestParam String query) {
        Map<String, List<Map<String, String>>> results = new HashMap<>();

        // Clients
        List<Map<String, String>> clientResults = new ArrayList<>();
        clientRepo.findByCompanyNameContainingIgnoreCaseOrContactPersonContainingIgnoreCaseOrEmailContainingIgnoreCaseOrPhoneContainingIgnoreCaseOrAddressContainingIgnoreCase(
                query, query, query, query, query
        ).forEach(c -> {
            Map<String, String> map = new HashMap<>();
            map.put("type", "client");
            map.put("id", String.valueOf(c.getId()));
            map.put("label", c.getCompanyName() + " (" + c.getEmail() + ")");
            clientResults.add(map);
        });
        results.put("clients", clientResults);

        // Invoices
        List<Map<String, String>> invoiceResults = new ArrayList<>();
        invoiceRepo.findByInvoiceNumberContainingIgnoreCase(query)
                .forEach(inv -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("type", "invoice");
                    map.put("id", String.valueOf(inv.getId()));
                    map.put("label", inv.getInvoiceNumber());
                    invoiceResults.add(map);
                });
        results.put("invoices", invoiceResults);

        // Documents
        List<Map<String, String>> documentResults = new ArrayList<>();
        documentRepo.findByDocumentTypeContainingIgnoreCase(query)  // Only one parameter
                .forEach(doc -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("type", "document");
                    map.put("id", String.valueOf(doc.getId()));
                    map.put("label", doc.getDocumentType() + " - " + doc.getFileName());
                    documentResults.add(map);
                });
        results.put("documents", documentResults);

        // Services
        List<Map<String, String>> serviceResults = new ArrayList<>();
        serviceRepo.findByServiceNameContainingIgnoreCase(query)
                .forEach(s -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("type", "service");
                    map.put("id", String.valueOf(s.getId()));
                    map.put("label", s.getServiceName());
                    serviceResults.add(map);
                });
        results.put("services", serviceResults);

        return results;
    }
}
