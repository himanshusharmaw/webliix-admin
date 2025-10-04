package com.webliix.service;

import com.webliix.model.Leads;
import com.webliix.model.LeadStatus;
import com.webliix.repository.LeadsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LeadsService {

    private final LeadsRepository leadsRepository;

    @Autowired
    public LeadsService(LeadsRepository leadsRepository) {
        this.leadsRepository = leadsRepository;
    }

    public List<Leads> getAllLeads() { 
        return leadsRepository.findAll(); 
    }

    public Optional<Leads> getLeadById(Long id) { 
        return leadsRepository.findById(id); 
    }

    public Leads createLead(Leads lead) {
        if (lead.getStatus() == null) {
            lead.setStatus(LeadStatus.NEW);
        }
        return leadsRepository.save(lead);
    }

    public Leads updateLead(Long id, Leads details) {
        Leads lead = leadsRepository.findById(id).orElseThrow(() -> new RuntimeException("Lead not found"));
        lead.setName(details.getName());
        lead.setContactPerson(details.getContactPerson());
        lead.setEmail(details.getEmail());
        lead.setPhone(details.getPhone());
        lead.setNotes(details.getNotes());
        lead.setStatus(details.getStatus());
        return leadsRepository.save(lead);
    }

    public void deleteLead(Long id) { 
        leadsRepository.deleteById(id); 
    }

    /**
     * Mark a lead as converted without creating a client.
     * @param leadId lead id
     * @param removeLead if true, delete lead after marking as converted
     * @return updated lead
     */
    @Transactional
    public Leads markLeadAsConverted(Long leadId, boolean removeLead) {
        Leads lead = leadsRepository.findById(leadId).orElseThrow(() -> new RuntimeException("Lead not found"));

        if (removeLead) {
            leadsRepository.delete(lead);
            return lead; 
        } else {
            lead.setStatus(LeadStatus.CONVERTED);
            return leadsRepository.save(lead);
        }
    }
}