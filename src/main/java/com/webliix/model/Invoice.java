package com.webliix.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    private String paymentInfo;
    private String invoiceNumber;
    private LocalDate issueDate;
    private LocalDate dueDate;

    private BigDecimal subTotle;   // Subtotal
    private BigDecimal discount;
    private Integer quantity;
    private Integer unitPrice;
    private BigDecimal amountPaid;
    private BigDecimal remaining;

    private String status; // DRAFT, SENT, PAID, OVERDUE

    @Lob
    private String notes;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // ✅ Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public String getPaymentInfo() { return paymentInfo; }
    public void setPaymentInfo(String paymentInfo) { this.paymentInfo = paymentInfo; }

    public String getInvoiceNumber() { return invoiceNumber; }
    public void setInvoiceNumber(String invoiceNumber) { this.invoiceNumber = invoiceNumber; }

    public LocalDate getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public BigDecimal getSubTotle() { return subTotle; }
    public void setSubTotle(BigDecimal subTotle) { this.subTotle = subTotle; }

    public BigDecimal getDiscount() { return discount; }
    public void setDiscount(BigDecimal discount) { this.discount = discount; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Integer getUnitPrice() { return unitPrice; }
    public void setUnitPrice(Integer unitPrice) { this.unitPrice = unitPrice; }

    public BigDecimal getAmountPaid() { return amountPaid; }
    public void setAmountPaid(BigDecimal amountPaid) { this.amountPaid = amountPaid; }

    public BigDecimal getRemaining() { return remaining; }
    public void setRemaining(BigDecimal remaining) { this.remaining = remaining; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
