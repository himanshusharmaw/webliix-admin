package com.webliix.service;

import com.webliix.model.Document;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;

public interface DocumentService {
    List<Document> findAll();
    Optional<Document> findById(Long id);
    Document save(Document document);
    void deleteById(Long id);
    Document update(Long id, Document documentDetails);
    List<Document> findByClientId(Long clientId);
    List<Document> findByClientIdAndDocumentType(Long clientId, String documentType);
    Document uploadDocument(MultipartFile file, Long clientId, String documentType);
    byte[] downloadDocument(Long id);
    void deleteDocument(Long id);
}