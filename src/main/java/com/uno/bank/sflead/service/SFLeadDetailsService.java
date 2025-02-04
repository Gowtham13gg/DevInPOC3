package com.uno.bank.sflead.service;

import com.uno.bank.sflead.model.SFLeadDetails;
import com.uno.bank.sflead.repository.SFLeadDetailsRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;

@Service
@RequiredArgsConstructor
public class SFLeadDetailsService {
    
    private final SFLeadDetailsRepository leadDetailsRepository;
    
    public SFLeadDetails saveLead(@Valid SFLeadDetails leadDetails) {
        if (leadDetailsRepository.existsByMobileNo(leadDetails.getMobileNo())) {
            throw new IllegalArgumentException("Mobile number already exists");
        }
        return leadDetailsRepository.save(leadDetails);
    }
}
