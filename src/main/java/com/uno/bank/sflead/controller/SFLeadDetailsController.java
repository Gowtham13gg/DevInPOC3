package com.uno.bank.sflead.controller;

import com.uno.bank.sflead.dto.ApiResponse;
import com.uno.bank.sflead.dto.LeadDetailsRequest;
import com.uno.bank.sflead.model.Address;
import com.uno.bank.sflead.model.ApplicationDetails;
import com.uno.bank.sflead.model.SFLeadDetails;
import com.uno.bank.sflead.service.SFLeadDetailsService;
import com.uno.bank.sflead.util.JsonUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/app/insert/SFLead/details")
@RequiredArgsConstructor
public class SFLeadDetailsController {
    
    private final SFLeadDetailsService leadDetailsService;
    private final JsonUtil jsonUtil;
    
    @PostMapping
    public ResponseEntity<ApiResponse<SFLeadDetails>> insertLeadDetails(@Valid @RequestBody LeadDetailsRequest request) {
        log.info("Received lead details request for applicationId: {}", request.getApplicationId());
        
        try {
            SFLeadDetails leadDetails = new SFLeadDetails();
            leadDetails.setApplicationId(request.getApplicationId());
            leadDetails.setLeadId(request.getLeadId());
            leadDetails.setClientId(request.getClientId());
            leadDetails.setMobileNo(request.getMobileNo());
            leadDetails.setLastScreen(request.getLastScreen());
            leadDetails.setApplicationStatus(request.getApplicationStatus());
            
            ApplicationDetails appDetails = new ApplicationDetails();
            appDetails.setPersonalDetails(jsonUtil.mapToJson(request.getPersonalDetails()));
            appDetails.setKycDetails(jsonUtil.mapToJson(request.getPersonalDetails()));
            appDetails.setOccupation(jsonUtil.mapToJson(request.getOccupationDetails()));
            appDetails.setConsent(jsonUtil.mapToJson(request.getConsentDetails()));
            appDetails.setAdditionalDetails(jsonUtil.mapToJson(request.getReferenceDetails()));
            
            List<Address> addresses = new ArrayList<>();
            if (request.getAddressDetails() != null) {
                for (Map.Entry<String, String> entry : request.getAddressDetails().entrySet()) {
                    Address address = new Address();
                    address.setAddressType(entry.getKey());
                    address.setAddressLine1(entry.getValue());
                    addresses.add(address);
                }
            }
            appDetails.setAddress(addresses);
            
            leadDetails.setApplicationDetails(appDetails);
            
            SFLeadDetails savedLead = leadDetailsService.saveLead(leadDetails);
            log.info("Successfully saved lead details for applicationId: {}", request.getApplicationId());
            return ResponseEntity.ok(ApiResponse.success(savedLead));
            
        } catch (IllegalArgumentException e) {
            log.error("Validation error for applicationId {}: {}", request.getApplicationId(), e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            log.error("Error processing lead details for applicationId {}: {}", request.getApplicationId(), e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.error("Internal server error occurred"));
        }
    }
}
