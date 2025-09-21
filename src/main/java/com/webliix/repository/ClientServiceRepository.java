package com.webliix.repository;

import com.webliix.model.ClientService;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientServiceRepository extends JpaRepository<ClientService, Long> {
    List<ClientService> findByServiceNameContainingIgnoreCase(String query);

	List<ClientService> findByClientId(Long clientId);
}
