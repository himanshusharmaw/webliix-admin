package com.webliix.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ClientServiceDTO {
    private Long id;
    private String serviceName;
    private String description;
    private Double price;  // use Double (not BigDecimal)
    private Integer durationInDays;
    private String status;
    private LocalDateTime deadline;   // use LocalDateTime
    private LocalDateTime startFrom;  // use LocalDateTime
    private Long clientId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ClientServiceDTO() {}

    public ClientServiceDTO(
            Long id,
            String serviceName,
            String description,
            Double price,                  // ✅ match entity
            Integer durationInDays,
            String status,
            LocalDateTime deadline,        // ✅ match entity
            LocalDateTime startFrom,       // ✅ match entity
            Long clientId,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.serviceName = serviceName;
        this.description = description;
        this.price = price;
        this.durationInDays = durationInDays;
        this.status = status;
        this.deadline = deadline;
        this.startFrom = startFrom;
        this.clientId = clientId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // ===== Getters & Setters =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getDurationInDays() { return durationInDays; }
    public void setDurationInDays(Integer durationInDays) { this.durationInDays = durationInDays; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getDeadline() { return deadline; }
    public void setDeadline(LocalDateTime deadline) { this.deadline = deadline; }

    public LocalDateTime getStartFrom() { return startFrom; }
    public void setStartFrom(LocalDateTime startFrom) { this.startFrom = startFrom; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }
}
