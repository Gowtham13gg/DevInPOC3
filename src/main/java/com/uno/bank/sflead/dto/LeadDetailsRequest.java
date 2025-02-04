package com.uno.bank.sflead.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import java.util.Map;
import com.uno.bank.sflead.validation.NotEmptyMap;

@Data
public class LeadDetailsRequest {
    @NotBlank(message = "Application ID is required")
    private String applicationId;
    
    @NotBlank(message = "Lead ID is required")
    private String leadId;
    
    @NotBlank(message = "Client ID is required")
    private String clientId;
    
    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
    private String mobileNo;
    
    private String lastScreen;
    private String applicationStatus;
    
    @NotEmptyMap(message = "Personal details are required")
    private Map<String, String> personalDetails;
    
    private Map<String, String> addressDetails;
    
    @NotEmptyMap(message = "Occupation details are required")
    private Map<String, String> occupationDetails;
    
    @NotEmptyMap(message = "Consent details are required")
    private Map<String, String> consentDetails;
    
    private Map<String, String> referenceDetails;
}
