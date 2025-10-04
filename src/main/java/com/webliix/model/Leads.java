package com.webliix.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;


@Entity
@Table(name = "leads")
public class Leads {


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;


@NotBlank
private String name;


private String contactPerson;


@Email
private String email;


private String phone;


@Enumerated(EnumType.STRING)
private LeadStatus status = LeadStatus.NEW;


@Column(length = 2000)
private String notes;



private LocalDateTime createdAt;
private LocalDateTime updatedAt;


@PrePersist
protected void onCreate() {
createdAt = LocalDateTime.now();
updatedAt = LocalDateTime.now();
}


@PreUpdate
protected void onUpdate() {
updatedAt = LocalDateTime.now();
}


// ===== Getters & Setters =====


public Long getId() { return id; }
public void setId(Long id) { this.id = id; }


public String getName() { return name; }
public void setName(String name) { this.name = name; }


public String getContactPerson() { return contactPerson; }
public void setContactPerson(String contactPerson) { this.contactPerson = contactPerson; }


public String getEmail() { return email; }
public void setEmail(String email) { this.email = email; }


public String getPhone() { return phone; }
public void setPhone(String phone) { this.phone = phone; }


public LeadStatus getStatus() { return status; }
public void setStatus(LeadStatus status) { this.status = status; }


public String getNotes() { return notes; }
public void setNotes(String notes) { this.notes = notes; }

public LocalDateTime getCreatedAt() {
    return createdAt;
}

public LocalDateTime getUpdatedAt() {
    return updatedAt;
}

}

