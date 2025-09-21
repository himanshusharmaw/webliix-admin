package com.webliix.service;

import com.webliix.model.Invoice;

import java.util.List;
import java.util.Optional;

public interface InvoiceService {
    List<Invoice> getAllInvoices();
    Optional<Invoice> getInvoiceById(Long id);
    Invoice createInvoice(Invoice invoice);
    Invoice updateInvoice(Long id, Invoice invoice);
    void deleteInvoice(Long id);
    List<Invoice> getInvoicesByClient(Long clientId);
    List<Invoice> getInvoicesByStatus(String status);
}
