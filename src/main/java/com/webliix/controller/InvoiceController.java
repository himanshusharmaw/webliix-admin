package com.webliix.controller;

import com.webliix.dto.InvoiceDTO;
import com.webliix.model.Invoice;
import com.webliix.service.InvoiceService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/invoices")
@CrossOrigin(origins = "http://localhost:5173") // allow frontend React
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    // ✅ return DTOs
    @GetMapping
    public List<InvoiceDTO> getAllInvoices() {
        return invoiceService.getAllInvoices()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDTO> getInvoiceById(@PathVariable Long id) {
        return invoiceService.getInvoiceById(id)
                .map(invoice -> ResponseEntity.ok(convertToDTO(invoice)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public InvoiceDTO createInvoice(@RequestBody Invoice invoice) {
        Invoice saved = invoiceService.createInvoice(invoice);
        return convertToDTO(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvoiceDTO> updateInvoice(@PathVariable Long id, @RequestBody Invoice invoice) {
        try {
            Invoice updated = invoiceService.updateInvoice(id, invoice);
            return ResponseEntity.ok(convertToDTO(updated));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/client/{clientId}")
    public List<InvoiceDTO> getInvoicesByClient(@PathVariable Long clientId) {
        return invoiceService.getInvoicesByClient(clientId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/status/{status}")
    public List<InvoiceDTO> getInvoicesByStatus(@PathVariable String status) {
        return invoiceService.getInvoicesByStatus(status)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ===== Helper method =====
    private InvoiceDTO convertToDTO(Invoice inv) {
        InvoiceDTO dto = new InvoiceDTO();
        dto.setId(inv.getId());
        dto.setClientId(inv.getClient() != null ? inv.getClient().getId() : null);
        dto.setInvoiceNumber(inv.getInvoiceNumber());
        dto.setIssueDate(inv.getIssueDate());
        dto.setDueDate(inv.getDueDate());

        // ✅ new fields
        dto.setSubTotle(inv.getSubTotle());
        dto.setDiscount(inv.getDiscount());
        dto.setQuantity(inv.getQuantity());
        dto.setUnitPrice(inv.getUnitPrice());
        dto.setAmountPaid(inv.getAmountPaid());
        dto.setRemaining(inv.getRemaining());
        dto.setPaymentInfo(inv.getPaymentInfo());

        dto.setStatus(inv.getStatus());
        dto.setNotes(inv.getNotes());
        dto.setCreatedAt(inv.getCreatedAt());
        return dto;
    }
}
