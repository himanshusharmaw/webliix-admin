package com.webliix.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.webliix.model.Client;
import com.webliix.model.Document;
import com.webliix.repository.ClientRepository;
import com.webliix.repository.DocumentRepository;
import com.webliix.service.DocumentService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DocumentServiceImpl implements DocumentService {
    
    @Autowired
    private DocumentRepository documentRepository;
    
    @Autowired
    private ClientRepository clientRepository;
    
    private final Path rootLocation = Paths.get("uploads/documents");
    
    @Override
    public List<Document> findAll() {
        return documentRepository.findAll();
    }
    
    @Override
    public Optional<Document> findById(Long id) {
        return documentRepository.findById(id);
    }
    
    @Override
    public Document save(Document document) {
        return documentRepository.save(document);
    }
    
    @Override
    public void deleteById(Long id) {
        documentRepository.deleteById(id);
    }
    
    @Override
    public Document update(Long id, Document documentDetails) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found with id: " + id));
        
        document.setDocumentType(documentDetails.getDocumentType());
        document.setFileName(documentDetails.getFileName());
        
        return documentRepository.save(document);
    }
    
    @Override
    public List<Document> findByClientId(Long clientId) {
        return documentRepository.findByClientId(clientId);
    }
    
    @Override
    public List<Document> findByClientIdAndDocumentType(Long clientId, String documentType) {
        return documentRepository.findByClientIdAndDocumentType(clientId, documentType);
    }
    
    @Override
    public Document uploadDocument(MultipartFile file, Long clientId, String documentType) {
        try {
            // Check if client exists
            Client client = clientRepository.findById(clientId)
                    .orElseThrow(() -> new RuntimeException("Client not found with id: " + clientId));
            
            // Create upload directory if it doesn't exist
            if (!Files.exists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }
            
            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.contains(".") 
                    ? originalFilename.substring(originalFilename.lastIndexOf(".")) 
                    : "";
            String uniqueFilename = UUID.randomUUID().toString() + fileExtension;
            
            // Save file to disk
            Path destinationFile = rootLocation.resolve(Paths.get(uniqueFilename))
                    .normalize().toAbsolutePath();
            Files.copy(file.getInputStream(), destinationFile);
            
            // Create document record
            Document document = new Document();
            document.setClient(client);
            document.setDocumentType(documentType);
            document.setFileName(originalFilename);
            document.setFilePath(destinationFile.toString());
            document.setFileSize(file.getSize());
            document.setMimeType(file.getContentType());
            document.setUploadedAt(LocalDateTime.now());
            
            return documentRepository.save(document);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file: " + e.getMessage());
        }
    }
    
    @Override
    public byte[] downloadDocument(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found with id: " + id));
        
        try {
            Path filePath = Paths.get(document.getFilePath());
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + e.getMessage());
        }
    }
    
    @Override
    public void deleteDocument(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found with id: " + id));
        
        try {
            // Delete file from disk
            Path filePath = Paths.get(document.getFilePath());
            Files.deleteIfExists(filePath);
            
            // Delete record from database
            documentRepository.deleteById(id);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file: " + e.getMessage());
        }
    }
}