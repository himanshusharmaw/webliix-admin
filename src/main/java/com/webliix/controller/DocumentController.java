package com.webliix.controller;

import com.webliix.model.Document;
import com.webliix.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin(origins = "http://localhost:3000")
public class DocumentController {
    
    @Autowired
    private DocumentService documentService;
    
    @GetMapping
    public List<Document> getAllDocuments() {
        return documentService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocumentById(@PathVariable Long id) {
        Optional<Document> document = documentService.findById(id);
        return document.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/client/{clientId}")
    public List<Document> getDocumentsByClientId(@PathVariable Long clientId) {
        return documentService.findByClientId(clientId);
    }
    
    @GetMapping("/client/{clientId}/type/{documentType}")
    public List<Document> getDocumentsByClientIdAndType(
            @PathVariable Long clientId, 
            @PathVariable String documentType) {
        return documentService.findByClientIdAndDocumentType(clientId, documentType);
    }
    
    @PostMapping("/upload")
    public ResponseEntity<Document> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("clientId") Long clientId,
            @RequestParam("documentType") String documentType) {
        
        try {
            Document document = documentService.uploadDocument(file, clientId, documentType);
            return ResponseEntity.status(HttpStatus.CREATED).body(document);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable Long id) {
        try {
            byte[] fileContent = documentService.downloadDocument(id);
            Document document = documentService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Document not found"));
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(document.getMimeType()));
            headers.setContentDispositionFormData("attachment", document.getFileName());
            headers.setContentLength(fileContent.length);
            
            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDocument(@PathVariable Long id) {
        try {
            documentService.deleteDocument(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}