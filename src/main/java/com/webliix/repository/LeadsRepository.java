package com.webliix.repository;

import com.webliix.model.Leads;
import com.webliix.model.LeadStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeadsRepository extends JpaRepository<Leads, Long> {
    List<Leads> findByStatus(LeadStatus status);
    List<Leads> findByNameContainingIgnoreCaseOrContactPersonContainingIgnoreCaseOrEmailContainingIgnoreCase(
        String name, String contactPerson, String email);
}
