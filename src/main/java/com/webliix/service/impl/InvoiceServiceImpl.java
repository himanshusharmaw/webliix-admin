package com.webliix.service.impl;

import com.webliix.model.Invoice;
import com.webliix.repository.InvoiceRepository;
import com.webliix.service.InvoiceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Override
    public Optional<Invoice> getInvoiceById(Long id) {
        return invoiceRepository.findById(id);
    }

    @Override
    public Invoice createInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Override
    @Transactional
    public Invoice updateInvoice(Long id, Invoice invoice) {
        return invoiceRepository.findById(id).map(existing -> {
            existing.setInvoiceNumber(invoice.getInvoiceNumber());
            existing.setIssueDate(invoice.getIssueDate());
            existing.setDueDate(invoice.getDueDate());
            existing.setSubTotle(invoice.getSubTotle());
            existing.setDiscount(invoice.getDiscount());
            existing.setQuantity(invoice.getQuantity());
            existing.setUnitPrice(invoice.getUnitPrice());
            existing.setAmountPaid(invoice.getAmountPaid());
            existing.setRemaining(invoice.getRemaining());
            existing.setPaymentInfo(invoice.getPaymentInfo());
            existing.setStatus(invoice.getStatus());
            existing.setNotes(invoice.getNotes());
            existing.setClient(invoice.getClient());
            return invoiceRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Invoice not found with id " + id));
    }

    @Override
    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getInvoicesByClient(Long clientId) {
        return invoiceRepository.findByClientId(clientId);
    }

    @Override
    public List<Invoice> getInvoicesByStatus(String status) {
        return invoiceRepository.findByStatus(status);
    }
}
