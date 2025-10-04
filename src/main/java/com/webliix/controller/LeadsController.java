package com.webliix.controller;

import com.webliix.model.LeadStatus;
import com.webliix.model.Leads;
import com.webliix.service.LeadsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/leads")
public class LeadsController {

    private final LeadsService leadsService;

    @Autowired
    public LeadsController(LeadsService leadsService) {
        this.leadsService = leadsService;
    }

    @GetMapping
    public List<Leads> getAllLeads() {
        return leadsService.getAllLeads();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Leads> getLeadById(@PathVariable Long id) {
        Optional<Leads> lead = leadsService.getLeadById(id);
        return lead.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Leads createLead(@RequestBody Leads lead) {
        return leadsService.createLead(lead);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Leads> updateLead(@PathVariable Long id, @RequestBody Leads leadDetails) {
        try {
            Leads updatedLead = leadsService.updateLead(id, leadDetails);
            return ResponseEntity.ok(updatedLead);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLead(@PathVariable Long id) {
        leadsService.deleteLead(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/statuses")
    public LeadStatus[] getStatuses() {
        return LeadStatus.values();
    }
}
