package com.webliix.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

import org.springframework.cglib.core.Local;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "services")
public class ClientService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String serviceName;

    private String description;

    private Double price;

    private Integer durationInDays;
    
    private String status; 

    private LocalDateTime deadline;
    
    private LocalDateTime startFrom;
    
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    @JsonBackReference(value = "client-services")
    private Client client;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
	}

	public LocalDateTime getStartFrom() {
		return startFrom;
	}

	public void setStartFrom(LocalDateTime startFrom) {
		this.startFrom = startFrom;
	}

	@PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    
    // ===== Getters and Setters =====
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

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
