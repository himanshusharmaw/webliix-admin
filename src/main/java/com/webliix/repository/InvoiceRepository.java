package com.webliix.repository;

import com.webliix.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByClientId(Long clientId);
    List<Invoice> findByStatus(String status);
    List<Invoice> findByInvoiceNumberContainingIgnoreCase(String invoiceNumber);
}
