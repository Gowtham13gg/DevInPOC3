package com.uno.bank.sflead.service;

import com.uno.bank.sflead.dto.LeadDetailsRequest;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import com.uno.bank.sflead.exception.LeadDetailsException;
import com.uno.bank.sflead.entity.SFLeadDetails;
import com.uno.bank.sflead.repository.SFLeadDetailsRepository;

@Service
@Slf4j
public class SFLeadDetailsService {
    
    @Autowired
    private SFLeadDetailsRepository leadDetailsRepository;
    
    public void insertLeadDetails(LeadDetailsRequest request) {
        log.info("Processing lead details for application ID: {}", request.getApplicationId());
        
        // Validate age if present in personalDetails
        if (request.getPersonalDetails() != null && request.getPersonalDetails().containsKey("age")) {
            String ageValue = request.getPersonalDetails().get("age");
            if (!ageValue.matches("\\d+")) {
                throw new LeadDetailsException("Age must be numeric only, if provided");
            }
            int age = Integer.parseInt(ageValue);
            if (age < 0 || age > 120) {
                throw new LeadDetailsException("Age must be between 0 and 120, if provided");
            }
        }

        // Map request to entity
        SFLeadDetails leadDetails = new SFLeadDetails();
        leadDetails.setApplicationId(request.getApplicationId());
        leadDetails.setLeadId(request.getLeadId());
        leadDetails.setClientId(request.getClientId());
        leadDetails.setMobileNo(request.getMobileNo());
        leadDetails.setLastScreen(request.getLastScreen());
        leadDetails.setApplicationStatus(request.getApplicationStatus());
        leadDetails.setPersonalDetails(request.getPersonalDetails());
        leadDetails.setOccupationDetails(request.getOccupationDetails());
        leadDetails.setConsentDetails(request.getConsentDetails());

        // Save to database
        leadDetailsRepository.save(leadDetails);
    }

    public SFLeadDetails findByMobileNo(String mobileNo) {
        return leadDetailsRepository.findByMobileNo(mobileNo)
            .orElseThrow(() -> new LeadDetailsException("No lead found for mobile number: " + mobileNo));
    }
}
