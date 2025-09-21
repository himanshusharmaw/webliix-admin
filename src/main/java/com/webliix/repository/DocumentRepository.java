package com.webliix.repository;

import com.webliix.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByClientId(Long clientId);		
    List<Document> findByClientIdAndDocumentType(Long clientId, String documentType);
    
    @Query("SELECT d FROM Document d WHERE d.client.id = :clientId AND d.documentType IN :types")
    List<Document> findByClientIdAndDocumentTypeIn(@Param("clientId") Long clientId, 
                                                  @Param("types") List<String> types);
    
    @Query("SELECT d.documentType, COUNT(d) FROM Document d GROUP BY d.documentType")
    List<Object[]> countDocumentsByType();
    
    @Query("SELECT d FROM Document d WHERE d.fileName LIKE %:fileName%")
    List<Document> findByFileNameContaining(@Param("fileName") String fileName);
    
    @Query("SELECT d FROM Document d WHERE d.client.id = :clientId AND d.fileName LIKE %:fileName%")
    List<Document> findByClientIdAndFileNameContaining(@Param("clientId") Long clientId, 
                                                      @Param("fileName") String fileName);
    
    // FIXED METHOD
    List<Document> findByDocumentTypeContainingIgnoreCase(String query);
}
