package com.webliix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webliix.model.Client;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    // Example custom finder methods
    Optional<Client> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<Client> findByCompanyName(String companyName);
    
 
    List<Client> findByCompanyNameContainingIgnoreCaseOrContactPersonContainingIgnoreCaseOrEmailContainingIgnoreCaseOrPhoneContainingIgnoreCaseOrAddressContainingIgnoreCase(
        String companyName, String contactPerson, String email, String phone, String address);
}
